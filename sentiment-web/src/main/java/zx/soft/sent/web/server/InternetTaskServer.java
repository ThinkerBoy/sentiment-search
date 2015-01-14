package zx.soft.sent.web.server;

import java.util.Properties;

import org.restlet.Component;
import org.restlet.data.Protocol;

import zx.soft.sent.web.application.InternetTaskApplication;
import zx.soft.utils.config.ConfigUtil;
import zx.soft.utils.jackson.ReplaceConvert;

/**
 * 全网搜索任务联合接口服务
 * 
 * 示例：http://localhost:3900/task
 * 
 * @author wanggang
 *
 */
public class InternetTaskServer {

	private final Component component;
	private final InternetTaskApplication internetTaskApplication;

	private final int PORT;

	public InternetTaskServer() {
		Properties props = ConfigUtil.getProps("web-server.properties");
		PORT = Integer.parseInt(props.getProperty("api.port"));
		component = new Component();
		internetTaskApplication = new InternetTaskApplication();
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		InternetTaskServer specialServer = new InternetTaskServer();
		specialServer.start();
	}

	public void start() {
		component.getServers().add(Protocol.HTTP, PORT);
		try {
			component.getDefaultHost().attach("/task", internetTaskApplication);
			ReplaceConvert.configureJacksonConverter();
			component.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void stop() {
		try {
			component.stop();
			internetTaskApplication.stop();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
