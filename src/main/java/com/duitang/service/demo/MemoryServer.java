package com.duitang.service.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import com.duitang.service.base.ClientFactory;
import com.duitang.service.base.MetricCenter;
import com.duitang.service.base.ServerBootstrap;
import com.duitang.service.data.MapData;

public class MemoryServer {

	final static String[] PARAMETER_KEYS = { "server", "client", "port", "host", "print", "thread", "loop", "msg",
	        "protocol", "map" };

	public static void main(String[] args) {
		Map<String, String> param = argsToMap(args);
		if (param.containsKey("client")) {
			runClient(param);
		} else if (!param.containsKey("server")) {
			printUsage();
			System.exit(1);
		} else {
			runServer(param);
		}

	}

	static void runServer(Map<String, String> param) {
		String port = "9999";
		if (param.containsKey("port")) {
			port = param.get("port");
		}
		String console_print = "-1";
		if (param.containsKey("print")) {
			console_print = param.get("print");
		}
		String protocol = "http";
		if (param.containsKey("protocol")) {
			protocol = param.get("protocol");
		}
		int p = Integer.valueOf(port);
		int s = Integer.valueOf(console_print);

		MemoryCacheService impl = new MemoryCacheService();
		ServerBootstrap boot = new ServerBootstrap();
		try {
			boot.addService(DemoService.class, impl);
			boot.startUp(p, protocol);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		if (s > 0) {
			MetricCenter.enableConsoleReporter(s);
		}
	}

	static void runClient(Map<String, String> param) {
		String port = "9999";
		if (param.containsKey("port")) {
			port = param.get("port");
		}
		String host = "localhost";
		if (param.containsKey("host")) {
			host = param.get("host");
		}
		String console_print = "-1";
		if (param.containsKey("print")) {
			console_print = param.get("print");
		}
		String thread = "10";
		if (param.containsKey("thread")) {
			thread = param.get("thread");
		}
		String loop = "10000";
		if (param.containsKey("loop")) {
			loop = param.get("loop");
		}
		String msg = "20000";
		if (param.containsKey("msg")) {
			msg = param.get("msg");
		}
		int m = Integer.valueOf(msg);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < m; i++) {
			sb.append("1");
		}
		msg = sb.toString();
		String protocol = "http";
		if (param.containsKey("protocol")) {
			protocol = param.get("protocol");
		}

		int t = Integer.valueOf(thread);
		int s = Integer.valueOf(console_print);
		int l = Integer.valueOf(loop);

		boolean usemap = false;
		if (param.containsKey("map")) {
			usemap = true;
		}

		ClientFactory<DemoService> fac = ClientFactory.createFactory(DemoService.class);
		fac.setUrl(protocol + "://" + host + ":" + port);

		LoadRunner.one = fac.create();
		CountDownLatch latch = new CountDownLatch(t);
		Thread[] ths = new Thread[t];
		for (int i = 0; i < ths.length; i++) {
			ths[i] = new Thread(new LoadRunner(latch, l, msg, fac, usemap));
			ths[i].start();
		}

		if (s > 0) {
			MetricCenter.enableConsoleReporter(s);
		}

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}

	static void printUsage() {
		System.out.println("support paramter: ");
		for (String s : PARAMETER_KEYS) {
			System.out.println("    --" + s + "=");
		}
	}

	static Map<String, String> argsToMap(String[] args) {
		Map<String, String> ret = new HashMap<String, String>();
		for (String item : args) {
			if (item.startsWith("--")) {
				String line = item.substring("--".length());
				String[] kv = line.split("=");
				if (kv.length == 2) {
					ret.put(kv[0], kv[1]);
				} else if (kv.length == 1) {
					ret.put(kv[0], "true");
				}
			}
		}
		return ret;
	}

}

class LoadRunner implements Runnable {

	static protected DemoService one;
	protected CountDownLatch latch;
	protected int loop;
	protected String msg;
	protected ClientFactory<DemoService> fac;
	protected String name;
	protected boolean usemap;

	public LoadRunner(CountDownLatch latch, int loop, String msg, ClientFactory<DemoService> fac, boolean usemap) {
		this.latch = latch;
		this.loop = loop;
		this.msg = msg;
		this.fac = fac;
		this.usemap = usemap;
		this.name = Thread.currentThread().getName();
	}

	@Override
	public void run() {
		int i = 0;
		int err = 0;
		long ts = System.currentTimeMillis();
		try {
			DemoService cli = null;
			String val = null;
			Map vvv = new HashMap();
			vvv.put("aaa", msg);
			MapData v = new MapData(vvv);
			MapData vv = null;
			try {
				cli = fac.create();
				if (usemap) {
					if (!cli.setmap(name, v)) {
						System.err.println("setting error: " + name);
					}
				} else {
					if (!cli.memory_setString(name, msg, 1000000)) {
						System.err.println("setting error: " + name);
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally {
				fac.release(cli);
			}
			for (i = 0; i < loop; i++) {
				try {
					// cli = one;
					cli = fac.create();
					if (usemap) {
						vv = cli.getmap(name);
						if (!vv.getData().containsKey("aaa")
						        || vv.getData().get("aaa").toString().length() != msg.length()) {
							throw new Exception("value error: " + vv.getData().toString());
						}
					} else {
						val = cli.memory_getString(name);
						if (val.length() != msg.length()) {
							throw new Exception("value error: " + val);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					err++;
				} finally {
					fac.release(cli);
				}
			}
		} finally {
			ts = System.currentTimeMillis() - ts;
			System.out.println(name + " running elapsed: " + ts + "ms with loop=[" + loop + "] @" + i + ", error="
			        + err);
			this.latch.countDown();
		}
	}
}