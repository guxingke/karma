package com.duitang.service.karma.trace.zipkin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.duitang.service.karma.boot.KarmaServerConfig;
import com.duitang.service.karma.support.NameUtil;
import com.duitang.service.karma.trace.TraceCell;

import zipkin.Annotation;
import zipkin.BinaryAnnotation;
import zipkin.Endpoint;
import zipkin.Span;
import zipkin.Span.Builder;

public class ZipkinUtils {

	@SuppressWarnings("deprecation")
	public static Endpoint addr = Endpoint.create(NameUtil.getInstanceTag().app, KarmaServerConfig.host,
			KarmaServerConfig.port);

	public static Span fromTraceCell(TraceCell tc) {
		return fromTraceCell(Arrays.asList(tc)).get(0);
	}

	public static List<Span> fromTraceCell(List<TraceCell> tcs) {
		List<Span> ret = new ArrayList<Span>();
		for (TraceCell tc : tcs) {
			Builder r = Span.builder().traceId(tc.traceId).name(tc.name).id(tc.spanId).timestamp(tc.timestamp)
					.duration((tc.ts2 - tc.timestamp));

			if (tc.parentId != null) {
				r.parentId(tc.parentId);
			}

			Annotation an1 = Annotation.create(tc.ts1, tc.type[0], addr);
			Annotation an2 = Annotation.create(tc.ts2, tc.type[1], addr);
			r.addAnnotation(an1).addAnnotation(an2);
			if (tc.err != null) {
				BinaryAnnotation err = BinaryAnnotation.create("error", tc.err, addr);
				r.addBinaryAnnotation(err);
			}
			if (tc.pid != null) {
				r.addBinaryAnnotation(BinaryAnnotation.create("pid", String.valueOf(tc.pid), addr));
			}
			if (tc.clazzName != null) {
				r.addBinaryAnnotation(BinaryAnnotation.create("class", tc.clazzName, addr));
			}
			if (tc.group != null) {
				r.addBinaryAnnotation(BinaryAnnotation.create("group", tc.group, addr));
			}
			ret.add(r.build());
		}
		return ret;
	}

}