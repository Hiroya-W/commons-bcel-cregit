begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|verifier
package|;
end_package

begin_comment
comment|/**  * VerifierFactoryObserver instances are notified when new Verifier instances are created.  *  *  * @see VerifierFactory#getVerifier(String)  * @see VerifierFactory#getVerifiers()  * @see VerifierFactory#attach(VerifierFactoryObserver)  * @see VerifierFactory#detach(VerifierFactoryObserver)  */
end_comment

begin_interface
specifier|public
interface|interface
name|VerifierFactoryObserver
block|{
comment|/**      * VerifierFactoryObserver instances are notified invoking this method. The String argument is the fully qualified class      * name of a class a new Verifier instance created by the VerifierFactory operates on.      */
name|void
name|update
parameter_list|(
name|String
name|s
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

