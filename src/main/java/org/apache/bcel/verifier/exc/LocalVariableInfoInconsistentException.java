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
comment|/**  * A LocalVariableInfoInconsistentException instance is thrown by the LocalVariableInfo class when it detects that the  * information it holds is inconsistent; this is normally due to inconsistent LocalVariableTable entries in the Code  * attribute of a certain Method object.  */
end_comment

begin_class
specifier|public
class|class
name|LocalVariableInfoInconsistentException
extends|extends
name|ClassConstraintException
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|2833180480144304190L
decl_stmt|;
comment|/**      * Constructs a new LocalVariableInfoInconsistentException with null as its error message string.      */
specifier|public
name|LocalVariableInfoInconsistentException
parameter_list|()
block|{
block|}
comment|/**      * Constructs a new LocalVariableInfoInconsistentException with the specified error message.      */
specifier|public
name|LocalVariableInfoInconsistentException
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
block|}
end_class

end_unit

