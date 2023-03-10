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
operator|.
name|exc
package|;
end_package

begin_comment
comment|/**  * Instances of this class are thrown by BCEL's class file verifier "JustIce" when a class file to verify does not pass  * one of the verification passes 2 or 3. Note that the pass 3 used by "JustIce" involves verification that is usually  * delayed to pass 4. The name of this class is justified by the Java Virtual Machine Specification, 2nd edition, page  * 164, 5.4.1 where verification as a part of the linking process is defined to be the verification happening in passes  * 2 and 3.  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|VerificationException
extends|extends
name|VerifierConstraintViolatedException
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|8012776320318623652L
decl_stmt|;
comment|/**      * Constructs a new VerificationException with null as its error message string.      */
name|VerificationException
parameter_list|()
block|{
block|}
comment|/**      * Constructs a new VerificationException with the specified error message.      */
name|VerificationException
parameter_list|(
specifier|final
name|String
name|message
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
comment|/**      * Constructs a new VerificationException with the specified error message and exception      */
name|VerificationException
parameter_list|(
specifier|final
name|String
name|message
parameter_list|,
specifier|final
name|Throwable
name|initCause
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|,
name|initCause
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

