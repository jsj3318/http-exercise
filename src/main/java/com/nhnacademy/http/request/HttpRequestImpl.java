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


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpRequestImpl implements HttpRequest {
    /* TODO#2 HttpRequest를 구현 합니다.
    *  test/java/com/nhnacademy/http/request/HttpRequestImplTest TestCode를 실행하고 검증 합니다.
    */

    private final Socket client;

    // 2번째 라인부터 내용들 저장하는 맵
    private HashMap<String, Object> headerMap = new HashMap<>();
    private HashMap<String, Object> attributeMap = new HashMap<>();

    private final String key_method = "method";
    private final String key_uri = "uri";
    private final String key_parametermap = "parametermap";


    public HttpRequestImpl(Socket socket) {
        this.client = socket;

        //http request 내용 받아서 쪼개서 맵에 전부 저장하기
        //첫 줄은 method uri version
        //두번째 줄 부터는 헤더명: 내용

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            log.debug("http request");

            //첫 줄 읽기  GET /welcome.html?name=TEST-NAME&content=TEST-CONTENT&send=send HTTP/1.1
            String line = bufferedReader.readLine();
            log.debug(line);
            String[] arr = line.split(" ");
            headerMap.put(key_method, arr[0].trim());

            // ? 뒤에 인자들 저장할 맵
            HashMap<String, String> parameterMap = new HashMap<>();

            // '?' 앞까지만 uri인것 같음
            // ?가 존재할 경우
            // 뒤에 있는 변수들도 따로 맵으로 저장해야함
            if(arr[1].contains("?")){
                //?의 인덱스
                int index = arr[1].indexOf("?");

                //? 앞 부분까지 uri로 저장
                headerMap.put(key_uri, arr[1].substring(0, index).trim());

                //? 뒷 부분 인자들 parameterMap에 저장
                //name=TEST-NAME&content=TEST-CONTENT&send=send
                String param = arr[1].substring(index+1);
                String[] params = param.split("&");
                for(String s : params){
                    String[] paramInfo = s.split("=");
                    parameterMap.put(paramInfo[0].trim(), paramInfo[1].trim());
                }

            }
            else{
                //?가 없으면 그냥 넣기
                headerMap.put(key_uri, arr[1].trim());
            }

            headerMap.put(key_parametermap, parameterMap);


            //나머지 줄 읽기
            //Host: test-vm.com:3000
            while(true){
                line = bufferedReader.readLine();
                log.debug(line);

                if(line == null || line.isBlank()){
                    break;
                }

                String[] hInfo = line.split(":");
                headerMap.put(hInfo[0].trim(), hInfo[1].trim());
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String getMethod() {
        return String.valueOf(headerMap.get(key_method));
    }

    @Override
    public String getParameter(String name) {
        return getParameterMap().get(name);
    }

    @Override
    public Map<String, String> getParameterMap() {
        return (Map<String, String>) headerMap.get(key_parametermap);
    }

    @Override
    public String getHeader(String name) {
        return String.valueOf(headerMap.get(name));
    }

    @Override
    public void setAttribute(String name, Object o) {
        attributeMap.put(name, o);
    }

    @Override
    public Object getAttribute(String name) {
        return attributeMap.get(name);
    }

    @Override
    public String getRequestURI() {
        return String.valueOf(headerMap.get(key_uri));
    }
}
