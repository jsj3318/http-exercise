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

package com.nhnacademy.http;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

@Slf4j
/* TODO#3 Java에서 Thread는 implements Runnable or extends Thread를 이용해서 Thread를 만들 수 있습니다.
*  implements Runnable을 사용하여 구현 합니다.
*/
public class HttpRequestHandler implements Runnable {
    private final Socket client;

    public HttpRequestHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {

        //TODO#4 simple-http-server-step1을 참고 하여 구현 합니다.

        StringBuilder requestBuilder = new StringBuilder();
        while(true){
            try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))
            ){
                log.debug("-----HTTP-REQUEST_start()");
                while(true){
                    String line = bufferedReader.readLine();
                    requestBuilder.append(line);
                    log.debug("{}", line);
                    if(line == null || line.isEmpty()){
                        break;
                    }
                }
                log.debug("-----HTTP-REQUEST_end()");
                StringBuilder responseBody = new StringBuilder();
                 /*
            <html>
                <body>
                    <h1>hello java</h1>
                </body>
            </html>
                */
                responseBody.append("<html>")
                        .append("<body>")
                        .append("<h1>hello vaja</h1>")
                        .append("</body>")
                        .append("</html>");

                StringBuilder responseHeader = new StringBuilder();
                responseHeader.append("HTTP/1.0 200 OK").append(System.lineSeparator())
                        .append("Server: HTTP server/0.1").append(System.lineSeparator())
                        .append("Content-type: text/html; charset=UTF-8").append(System.lineSeparator())
                        .append("Connection: Closed").append(System.lineSeparator())
                        .append("Content-Length: ").append(responseBody.length()).append(System.lineSeparator());

                bufferedWriter.write(responseHeader + System.lineSeparator());
                bufferedWriter.write(responseBody.toString());
                bufferedWriter.flush();

                log.debug("header:{}", responseHeader);
                log.debug("body:{}", responseBody);


            }catch (IOException e){
                log.error("{}",e);
            }finally {
                try {
                    client.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }
}
