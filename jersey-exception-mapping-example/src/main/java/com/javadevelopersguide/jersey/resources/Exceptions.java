/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2010-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.javadevelopersguide.jersey.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exceptions class.
 *
 * @author Santiago.Pericas-Geertsen at oracle.com
 */
public class Exceptions {

    // -- Exceptions
    public static class MyException extends RuntimeException {

        private Response response;

        public MyException(Response response) {
            this.response = response;
        }

        public Response getResponse() {
            return response;
        }
    }

    public static class MySubException extends MyException {

        public MySubException(Response response) {
            super(response);
        }
    }

    public static class MySubSubException extends MySubException {

        public MySubSubException(Response response) {
            super(response);
        }
    }

    // -- Exception Mappers
    @Provider
    public static class MyExceptionMapper implements ExceptionMapper<MyException> {

        @Override
        public Response toResponse(MyException exception) {
            Response r = exception.getResponse();
            return Response.status(r.getStatus()).entity(
                    "Code:" + r.getStatus() + ":" + exception.getMessage()).build();
        }
    }

    @Provider
    public static class MySubExceptionMapper implements ExceptionMapper<MySubException> {

        @Override
        public Response toResponse(MySubException exception) {
            Response r = exception.getResponse();
            return Response.status(r.getStatus()).entity(
                    "Code:" + r.getStatus() + ":" + exception.getMessage()).build();
        }
    }

    @Provider
    public static class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

        @Override
        public Response toResponse(WebApplicationException exception) {
            Response r = exception.getResponse();
            return Response.status(r.getStatus()).entity("Code:" + r.getStatus() + ":"
                    + exception.getMessage()).build();
        }
    }
}
