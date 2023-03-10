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
name|generic
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
import|;
end_import

begin_comment
comment|/**  * Returnaddress, the type JSR or JSR_W instructions push upon the stack.  *  * see vmspec2 ï¿½3.3.3  */
end_comment

begin_class
specifier|public
class|class
name|ReturnaddressType
extends|extends
name|Type
block|{
specifier|public
specifier|static
specifier|final
name|ReturnaddressType
name|NO_TARGET
init|=
operator|new
name|ReturnaddressType
argument_list|()
decl_stmt|;
specifier|private
name|InstructionHandle
name|returnTarget
decl_stmt|;
comment|/**      * A Returnaddress [that doesn't know where to return to].      */
specifier|private
name|ReturnaddressType
parameter_list|()
block|{
name|super
argument_list|(
name|Const
operator|.
name|T_ADDRESS
argument_list|,
literal|"<return address>"
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a ReturnaddressType object with a target.      */
specifier|public
name|ReturnaddressType
parameter_list|(
specifier|final
name|InstructionHandle
name|returnTarget
parameter_list|)
block|{
name|super
argument_list|(
name|Const
operator|.
name|T_ADDRESS
argument_list|,
literal|"<return address targeting "
operator|+
name|returnTarget
operator|+
literal|">"
argument_list|)
expr_stmt|;
name|this
operator|.
name|returnTarget
operator|=
name|returnTarget
expr_stmt|;
block|}
comment|/**      * Returns if the two Returnaddresses refer to the same target.      */
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
specifier|final
name|Object
name|rat
parameter_list|)
block|{
if|if
condition|(
operator|!
operator|(
name|rat
operator|instanceof
name|ReturnaddressType
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
specifier|final
name|ReturnaddressType
name|that
init|=
operator|(
name|ReturnaddressType
operator|)
name|rat
decl_stmt|;
if|if
condition|(
name|this
operator|.
name|returnTarget
operator|==
literal|null
operator|||
name|that
operator|.
name|returnTarget
operator|==
literal|null
condition|)
block|{
return|return
name|that
operator|.
name|returnTarget
operator|==
name|this
operator|.
name|returnTarget
return|;
block|}
return|return
name|that
operator|.
name|returnTarget
operator|.
name|equals
argument_list|(
name|this
operator|.
name|returnTarget
argument_list|)
return|;
block|}
comment|/**      * @return the target of this ReturnaddressType      */
specifier|public
name|InstructionHandle
name|getTarget
parameter_list|()
block|{
return|return
name|returnTarget
return|;
block|}
comment|/**      * @return a hash code value for the object.      */
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
if|if
condition|(
name|returnTarget
operator|==
literal|null
condition|)
block|{
return|return
literal|0
return|;
block|}
return|return
name|returnTarget
operator|.
name|hashCode
argument_list|()
return|;
block|}
block|}
end_class

end_unit

