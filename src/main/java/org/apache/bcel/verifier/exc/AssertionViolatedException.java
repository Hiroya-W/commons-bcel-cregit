begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  *  */
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
comment|/**  * Instances of this class should never be thrown. When such an instance is thrown,  * this is due to an INTERNAL ERROR of BCEL's class file verifier&quot;JustIce&quot;.  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|AssertionViolatedException
extends|extends
name|RuntimeException
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|129822266349567409L
decl_stmt|;
comment|/** The error message. */
specifier|private
name|String
name|detailMessage
decl_stmt|;
comment|/** Constructs a new AssertionViolatedException with null as its error message string. */
specifier|public
name|AssertionViolatedException
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/**      * Constructs a new AssertionViolatedException with the specified error message preceded      * by&quot;INTERNAL ERROR:&quot;.      */
specifier|public
name|AssertionViolatedException
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|super
argument_list|(
name|message
operator|=
literal|"INTERNAL ERROR: "
operator|+
name|message
argument_list|)
expr_stmt|;
comment|// Thanks to Java, the constructor call here must be first.
name|detailMessage
operator|=
name|message
expr_stmt|;
block|}
comment|/**      * Constructs a new AssertionViolationException with the specified error message and initial cause      * @since 6.0      */
specifier|public
name|AssertionViolatedException
parameter_list|(
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
operator|=
literal|"INTERNAL ERROR: "
operator|+
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
comment|/** Extends the error message with a string before ("pre") and after ("post") the         'old' error message. All of these three strings are allowed to be null, and null         is always replaced by the empty string (""). In particular, after invoking this         method, the error message of this object can no longer be null.     */
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
comment|/**      * Returns the error message string of this AssertionViolatedException object.      * @return the error message string of this AssertionViolatedException.      */
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
comment|/**      * DO NOT USE. It's for experimental testing during development only.      */
specifier|public
specifier|static
name|void
name|main
parameter_list|(
specifier|final
name|String
index|[]
name|args
parameter_list|)
block|{
specifier|final
name|AssertionViolatedException
name|ave
init|=
operator|new
name|AssertionViolatedException
argument_list|(
literal|"Oops!"
argument_list|)
decl_stmt|;
name|ave
operator|.
name|extendMessage
argument_list|(
literal|"\nFOUND:\n\t"
argument_list|,
literal|"\nExiting!!\n"
argument_list|)
expr_stmt|;
throw|throw
name|ave
throw|;
block|}
block|}
end_class

end_unit

