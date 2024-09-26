/*
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * + Copyright 2024. NHN Academy Corp. All rights reserved.
 * + * While every precaution has been taken in the preparation of this resource,  assumes no
 * + responsibility for errors or omissions, or for damages resulting from the use of the information
 * + contained herein
 * + No part of this resource may be reproduced, stored in a retrieval system, or transmitted, in any
 * + form or by any means, electronic, mechanical, photocopying, recording, or otherwise, without the
 * + prior written permission.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package com.nhnacademy.http.request;

import lombok.extern.slf4j.Slf4j;


import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpRequestImpl implements HttpRequest {
    /* TODO#2 HttpRequest를 구현 합니다.
    *  test/java/com/nhnacademy/http/request/HttpRequestImplTest TestCode를 실행하고 검증 합니다.
    */

    private final Socket client;
    private HashMap<String, String> headerMap = new HashMap<>();
    private HashMap<String, Object> attributeMap = new HashMap<>();

    private final String key_method = "method";


    public HttpRequestImpl(Socket socket) {
        this.client = socket;
    }


    @Override
    public String getMethod() {
        return headerMap.get(key_method);
    }

    @Override
    public String getParameter(String name) {
        return headerMap.get(name);
    }

    @Override
    public Map<String, String> getParameterMap() {
        return null;
    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    @Override
    public void setAttribute(String name, Object o) {

    }

    @Override
    public Object getAttribute(String name) {
        return null;
    }

    @Override
    public String getRequestURI() {
        return null;
    }
}
