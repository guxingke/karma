package com.duitang.service.karma.codecs;

import java.util.HashSet;
import java.util.Map;

public class Set2Json extends JsonEncoding<Map> {

	@Override
	protected Class getResultType() {
		return HashSet.class;
	}

}