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
comment|/**  * Instances of this class are thrown by BCEL's class file verifier "JustIce" whenever verification proves that some  * constraint of a class file (as stated in the Java Virtual Machine Specification, Edition 2) is violated. This is  * roughly equivalent to the VerifyError the JVM-internal verifiers throw.  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|VerifierConstraintViolatedException
extends|extends
name|RuntimeException
block|{
comment|// /** The name of the offending class that did not pass the verifier. */
comment|// String name_of_offending_class;
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|2946136970490179465L
decl_stmt|;
comment|/** The specified error message. */
specifier|private
name|String
name|detailMessage
decl_stmt|;
comment|/**      * Constructs a new VerifierConstraintViolatedException with null as its error message string.      */
name|VerifierConstraintViolatedException
parameter_list|()
block|{
block|}
comment|/**      * Constructs a new VerifierConstraintViolatedException with the specified error message.      */
name|VerifierConstraintViolatedException
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
comment|// Not that important
name|detailMessage
operator|=
name|message
expr_stmt|;
block|}
comment|/**      * Constructs a new VerifierConstraintViolationException with the specified error message and cause      */
name|VerifierConstraintViolatedException
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
name|detailMessage
operator|=
name|message
expr_stmt|;
block|}
comment|/**      * Extends the error message with a string before ("pre") and after ("post") the 'old' error message. All of these three      * strings are allowed to be null, and null is always replaced by the empty string (""). In particular, after invoking      * this method, the error message of this object can no longer be null.      */
specifier|public
name|void
name|extendMessage
parameter_list|(
name|String
name|pre
parameter_list|,
name|String
name|post
parameter_list|)
block|{
if|if
condition|(
name|pre
operator|==
literal|null
condition|)
block|{
name|pre
operator|=
literal|""
expr_stmt|;
block|}
if|if
condition|(
name|detailMessage
operator|==
literal|null
condition|)
block|{
name|detailMessage
operator|=
literal|""
expr_stmt|;
block|}
if|if
condition|(
name|post
operator|==
literal|null
condition|)
block|{
name|post
operator|=
literal|""
expr_stmt|;
block|}
name|detailMessage
operator|=
name|pre
operator|+
name|detailMessage
operator|+
name|post
expr_stmt|;
block|}
comment|/**      * Returns the error message string of this VerifierConstraintViolatedException object.      *      * @return the error message string of this VerifierConstraintViolatedException.      */
annotation|@
name|Override
specifier|public
name|String
name|getMessage
parameter_list|()
block|{
return|return
name|detailMessage
return|;
block|}
block|}
end_class

end_unit

