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
name|commons
operator|.
name|bcel6
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
name|commons
operator|.
name|bcel6
operator|.
name|ExceptionConstants
import|;
end_import

begin_comment
comment|/**   * ANEWARRAY -  Create new array of references  *<PRE>Stack: ..., count -&gt; ..., arrayref</PRE>  *  * @version $Id$  */
end_comment

begin_class
specifier|public
class|class
name|ANEWARRAY
extends|extends
name|CPInstruction
implements|implements
name|LoadClass
implements|,
name|AllocationInstruction
implements|,
name|ExceptionThrower
implements|,
name|StackConsumer
implements|,
name|StackProducer
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|3720173810934984310L
decl_stmt|;
comment|/**      * Empty constructor needed for the Class.newInstance() statement in      * Instruction.readInstruction(). Not to be used otherwise.      */
name|ANEWARRAY
parameter_list|()
block|{
block|}
specifier|public
name|ANEWARRAY
parameter_list|(
name|int
name|index
parameter_list|)
block|{
name|super
argument_list|(
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|bcel6
operator|.
name|Constants
operator|.
name|ANEWARRAY
argument_list|,
name|index
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Class
argument_list|<
name|?
argument_list|>
index|[]
name|getExceptions
parameter_list|()
block|{
name|Class
argument_list|<
name|?
argument_list|>
index|[]
name|cs
init|=
operator|new
name|Class
index|[
literal|1
operator|+
name|ExceptionConstants
operator|.
name|EXCS_CLASS_AND_INTERFACE_RESOLUTION
operator|.
name|length
index|]
decl_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|ExceptionConstants
operator|.
name|EXCS_CLASS_AND_INTERFACE_RESOLUTION
argument_list|,
literal|0
argument_list|,
name|cs
argument_list|,
literal|0
argument_list|,
name|ExceptionConstants
operator|.
name|EXCS_CLASS_AND_INTERFACE_RESOLUTION
operator|.
name|length
argument_list|)
expr_stmt|;
name|cs
index|[
name|ExceptionConstants
operator|.
name|EXCS_CLASS_AND_INTERFACE_RESOLUTION
operator|.
name|length
index|]
operator|=
name|ExceptionConstants
operator|.
name|NEGATIVE_ARRAY_SIZE_EXCEPTION
expr_stmt|;
return|return
name|cs
return|;
block|}
comment|/**      * Call corresponding visitor method(s). The order is:      * Call visitor methods of implemented interfaces first, then      * call methods according to the class hierarchy in descending order,      * i.e., the most specific visitXXX() call comes last.      *      * @param v Visitor object      */
annotation|@
name|Override
specifier|public
name|void
name|accept
parameter_list|(
name|Visitor
name|v
parameter_list|)
block|{
name|v
operator|.
name|visitLoadClass
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|v
operator|.
name|visitAllocationInstruction
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|v
operator|.
name|visitExceptionThrower
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|v
operator|.
name|visitStackProducer
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|v
operator|.
name|visitTypedInstruction
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|v
operator|.
name|visitCPInstruction
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|v
operator|.
name|visitANEWARRAY
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ObjectType
name|getLoadClassType
parameter_list|(
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
name|Type
name|t
init|=
name|getType
argument_list|(
name|cpg
argument_list|)
decl_stmt|;
if|if
condition|(
name|t
operator|instanceof
name|ArrayType
condition|)
block|{
name|t
operator|=
operator|(
operator|(
name|ArrayType
operator|)
name|t
operator|)
operator|.
name|getBasicType
argument_list|()
expr_stmt|;
block|}
return|return
operator|(
name|t
operator|instanceof
name|ObjectType
operator|)
condition|?
operator|(
name|ObjectType
operator|)
name|t
else|:
literal|null
return|;
block|}
block|}
end_class

end_unit

