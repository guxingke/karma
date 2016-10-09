package com.duitang.service.karma.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.duitang.service.karma.KarmaException;
import com.duitang.service.karma.KarmaRuntimeException;
import com.duitang.service.karma.client.KarmaClient;
import com.duitang.service.karma.support.InstanceTagHolder;
import com.duitang.service.karma.support.NameUtil;

public abstract class ClientFactory<T> implements ServiceFactory<T> {

	final static protected Logger err = LoggerFactory.getLogger("error");
	protected String url;
	protected List<String> serviceURL;
	protected long timeout = 1000;
	protected String clientid;
	protected int sz = 0;
	protected String group = "default";

	public ClientFactory() {
		this(null);
	}

	public ClientFactory(String clientid) {
		this.clientid = clientid;
		initClientName();
	}

	private void initClientName() {
		if (clientid == null) {
			clientid = NameUtil.genClientIdFromCode() + "@" + InstanceTagHolder.INSTANCE_TAG.app;
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
		String[] urlitems = url.split(";");
		this.serviceURL = new ArrayList<>();
		Collections.addAll(this.serviceURL, urlitems);
		this.sz = this.serviceURL.size();
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T create() { // for python performance issue no exception
		if (sz == 0) {
			new KarmaRuntimeException("no remote url find? please setUrl(String url)").printStackTrace();
			return null;
		}
		KarmaClient<T> ret;
		try {
			ret = KarmaClient.createKarmaClient(getServiceType(), serviceURL, group, getTimeout());
			return ret.getService();
		} catch (KarmaException e) {
			err.error("failed to create karma client: ", e);
		}
		return null;
	}

	public void release(T client) {
		// current for keep consistent
	}

	public static <T1> ClientFactory<T1> createFactory(final Class<T1> clz) {
		final String name = clz.getName();
		return new ClientFactory<T1>() {
			@Override
			public String getServiceName() {
				return name;
			}

			@SuppressWarnings("rawtypes")
			@Override
			public Class getServiceType() {
				return clz;
			}
		};
	}

	public void reset() throws KarmaException {
		KarmaClient.reset(group, serviceURL);
	}
	
}
