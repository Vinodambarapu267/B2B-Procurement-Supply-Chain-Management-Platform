
package com.example.demo.filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

@Component
public class RouteValidator {

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    private static final List<String> OPEN_GET_ENDPOINTS = List.of(
            "/actuator/**",
            "/api/v1/auth/**",
            "/api/v1/user/**",
            "/api/v1/purchase-requests/**"
            ,"/api/v1/appprovals/**"
    );

    private static final List<String> OPEN_POST_ENDPOINTS = List.of(
          "/api/v1/auth/**",
          "/api/v1/user/**",
          "/api/v1/purchase-requests/**",
          "/api/v1/appprovals/**",
          "/api/v1/purchase-orders/**"
          
    );

	public final Predicate<ServerHttpRequest> isSecured = request -> {
		String path = request.getURI().getPath();
		HttpMethod method = request.getMethod();

		boolean isOpenGet = HttpMethod.GET.equals(method)
				&& OPEN_GET_ENDPOINTS.stream().anyMatch(pattern -> PATH_MATCHER.match(pattern, path));

		boolean isOpenPost = HttpMethod.POST.equals(method)
				&& OPEN_POST_ENDPOINTS.stream().anyMatch(pattern -> PATH_MATCHER.match(pattern, path));

		return !(isOpenGet || isOpenPost);
	};
}
