package com.allinfinance.mss.socket.server;

import com.allinfinance.mss.constant.PfsNfRespEnum;
import com.allinfinance.mss.constant.PfsNfTxnEnum;
import com.allinfinance.mss.constant.XmlName;
import com.allinfinance.mss.service.MssBizSevice;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

public class NfServerIoHandler extends IoHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(NfServerIoHandler.class);

    private static final String REGEX = ",";
    private List<String> orgIds = null;
    private List<String> serviceIds = null;
    private String encode = "UTF-8";
    private MssBizSevice mssBizSevice;

    public NfServerIoHandler(String orgIds, String serviceIds, String encode, MssBizSevice mssBizSevice) {
        super();
        if (StringUtils.isNotBlank(orgIds)) {
            String[] orgs = orgIds.split(REGEX);
            if (orgs.length > 0) {
                this.orgIds = Arrays.asList(orgs);
                logger.info("允许交易机构! orgIds:" + orgIds);
            }
        }
        if (orgIds == null) {
            logger.info("不限制交易机构!");
        }
        if (StringUtils.isNotBlank(serviceIds)) {
            String[] services = serviceIds.split(REGEX);
            if (services.length > 0) {
                this.serviceIds = Arrays.asList(services);
                logger.info("允许服务接口! serviceIds:" + serviceIds);
            }
        }
        if (serviceIds == null) {
            logger.info("不限制服务接口!");
        }
        if (StringUtils.isNotBlank(encode)) {
            this.encode = encode;
        }
        this.mssBizSevice = mssBizSevice;
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        logger.info("连接创建@" + session);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        String clientIP = ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();
        logger.info("连接打开@" + session + ",远端IP:" + clientIP);
    }

    @Override
    public void inputClosed(IoSession session) throws Exception {
        // TODO: 2017/7/20 半关闭处理
//        logger.info("对端链接关闭@" + session);
//        session.closeOnFlush();
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        logger.info("连接关闭@" + session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        logger.error("连接超时@" + session);
        session.closeNow();
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        logger.error("连接异常@" + session, cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        long startTime = System.currentTimeMillis();
        long endTime;

        String reqMsg = (String) message;
        String respMsg = "";

        String serviceSn = null;
        String serviceId = null;
        String org = null;

        logger.info("请求报文:" + reqMsg);

        try {
            Document document = DocumentHelper.parseText(reqMsg);
            Element rootElement = document.getRootElement();
            Element headerElement = rootElement.element(XmlName.SERVICE_HEADER);
            serviceSn = headerElement.element(XmlName.SERVICE_SN).getTextTrim();
            serviceId = headerElement.element(XmlName.SERVICE_ID).getTextTrim();
            org = headerElement.element(XmlName.ORG).getTextTrim();

            logger.info("[REQUEST] SERVICE_ID:" + serviceId + ", SERVICE_SN:" + serviceSn + ", ORG:" + org);

            if (orgIds != null && !orgIds.contains(org)) {
                logger.error("不允许的接入机构! [RESPONSE] SERVICE_ID:" + serviceId + ", SERVICE_SN:" + serviceSn + ", ORG:" + org);
                ErrorResponse(session, reqMsg, PfsNfRespEnum.STATUS_S014);
                return;
            }
            if (serviceIds != null && !serviceIds.contains(serviceId)) {
                logger.error("不允许的服务接口! [RESPONSE] SERVICE_ID:" + serviceId + ", SERVICE_SN:" + serviceSn + ", ORG:" + org);
                ErrorResponse(session, reqMsg, PfsNfRespEnum.STATUS_S002);
                return;
            }
            if (PfsNfTxnEnum.NF_XXXXX.getCode().equals(serviceId)) {
                // TODO: 2019/3/19 调用业务服务

            }

            endTime = System.currentTimeMillis();
            logger.info("[RESPONSE] SERVICE_ID:" + serviceId + ", SERVICE_SN:" + serviceSn + ", ORG:" + org + ", time" + (endTime - startTime));
            session.write(respMsg);
        } catch (Exception e) {
            endTime = System.currentTimeMillis();
            logger.error("报文格式错误! [RESPONSE] SERVICE_ID:" + serviceId + ", SERVICE_SN:" + serviceSn + ", ORG:" + org + ", time" + (endTime - startTime));
            logger.error("异常!", e);
            ErrorResponse(session, reqMsg, PfsNfRespEnum.STATUS_S003);
        } finally {
            if (session != null)
                session.closeOnFlush();
        }
    }

    private void ErrorResponse(IoSession session, String message, PfsNfRespEnum respCode) {
        String errorResp = "";
        try {
            Document document = DocumentHelper.parseText(message);
            Element root = document.getRootElement();
            Element serviceBody = root.element(XmlName.SERVICE_BODY);
            if (serviceBody != null)
                root.remove(serviceBody);
            Element serviceHeader = root.element(XmlName.SERVICE_HEADER);
            Element servResponse = serviceHeader.addElement(XmlName.SERV_RESPONSE);

            servResponse.addElement(XmlName.SERV_RESP_STATUS).addText(PfsNfRespEnum.STATUS_FAIL.getCode());
            servResponse.addElement(XmlName.SERV_RESP_CODE).addText(respCode.getCode().substring(1));
            servResponse.addElement(XmlName.SERV_RESP_DESC).addText(respCode.getDesc());

            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setTrimText(false);
            format.setEncoding(encode);
            StringWriter stringWriter = new StringWriter();
            XMLWriter writer = new XMLWriter(stringWriter, format);
            writer.write(document);
            writer.flush();
            errorResp = stringWriter.toString();
        } catch (Exception e) {
            logger.error("生成错误应答失败! message:" + message);
            logger.error("异常!", e);
            errorResp = "";
        } finally {
            session.write(errorResp);
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
    }
}
