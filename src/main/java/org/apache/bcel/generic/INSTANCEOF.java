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
name|generic
package|;
end_package

begin_comment
comment|/**   * INSTANCEOF - Determine if object is of given type  *<PRE>Stack: ..., objectref -&gt; ..., result</PRE>  *  * @version $Id$  * @author<A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>  */
end_comment

begin_class
specifier|public
class|class
name|INSTANCEOF
extends|extends
name|CPInstruction
implements|implements
name|LoadClass
implements|,
name|ExceptionThrower
implements|,
name|StackProducer
implements|,
name|StackConsumer
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|1068668479062613915L
decl_stmt|;
comment|/**      * Empty constructor needed for the Class.newInstance() statement in      * Instruction.readInstruction(). Not to be used otherwise.      */
name|INSTANCEOF
parameter_list|()
block|{
block|}
specifier|public
name|INSTANCEOF
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
name|bcel
operator|.
name|Constants
operator|.
name|INSTANCEOF
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
return|return
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|ExceptionConstants
operator|.
name|EXCS_CLASS_AND_INTERFACE_RESOLUTION
return|;
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
name|visitStackConsumer
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
name|visitINSTANCEOF
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

