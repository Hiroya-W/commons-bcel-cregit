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

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|DataOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|ExceptionConstants
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|ConstantPool
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|util
operator|.
name|ByteSequence
import|;
end_import

begin_comment
comment|/**   * MULTIANEWARRAY - Create new mutidimensional array of references  *<PRE>Stack: ..., count1, [count2, ...] -&gt; ..., arrayref</PRE>  *  * @version $Id$  * @author<A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>  */
end_comment

begin_class
specifier|public
class|class
name|MULTIANEWARRAY
extends|extends
name|CPInstruction
implements|implements
name|LoadClass
implements|,
name|AllocationInstruction
implements|,
name|ExceptionThrower
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|7439639244808941662L
decl_stmt|;
specifier|private
name|short
name|dimensions
decl_stmt|;
comment|/**      * Empty constructor needed for the Class.newInstance() statement in      * Instruction.readInstruction(). Not to be used otherwise.      */
name|MULTIANEWARRAY
parameter_list|()
block|{
block|}
specifier|public
name|MULTIANEWARRAY
parameter_list|(
name|int
name|index
parameter_list|,
name|short
name|dimensions
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
name|MULTIANEWARRAY
argument_list|,
name|index
argument_list|)
expr_stmt|;
if|if
condition|(
name|dimensions
operator|<
literal|1
condition|)
block|{
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Invalid dimensions value: "
operator|+
name|dimensions
argument_list|)
throw|;
block|}
name|this
operator|.
name|dimensions
operator|=
name|dimensions
expr_stmt|;
name|length
operator|=
literal|4
expr_stmt|;
block|}
comment|/**      * Dump instruction as byte code to stream out.      * @param out Output stream      */
annotation|@
name|Override
specifier|public
name|void
name|dump
parameter_list|(
name|DataOutputStream
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|out
operator|.
name|writeByte
argument_list|(
name|opcode
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeShort
argument_list|(
name|index
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeByte
argument_list|(
name|dimensions
argument_list|)
expr_stmt|;
block|}
comment|/**      * Read needed data (i.e., no. dimension) from file.      */
annotation|@
name|Override
specifier|protected
name|void
name|initFromFile
parameter_list|(
name|ByteSequence
name|bytes
parameter_list|,
name|boolean
name|wide
parameter_list|)
throws|throws
name|IOException
block|{
name|super
operator|.
name|initFromFile
argument_list|(
name|bytes
argument_list|,
name|wide
argument_list|)
expr_stmt|;
name|dimensions
operator|=
name|bytes
operator|.
name|readByte
argument_list|()
expr_stmt|;
name|length
operator|=
literal|4
expr_stmt|;
block|}
comment|/**      * @return number of dimensions to be created      */
specifier|public
specifier|final
name|short
name|getDimensions
parameter_list|()
block|{
return|return
name|dimensions
return|;
block|}
comment|/**      * @return mnemonic for instruction      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|(
name|boolean
name|verbose
parameter_list|)
block|{
return|return
name|super
operator|.
name|toString
argument_list|(
name|verbose
argument_list|)
operator|+
literal|" "
operator|+
name|index
operator|+
literal|" "
operator|+
name|dimensions
return|;
block|}
comment|/**      * @return mnemonic for instruction with symbolic references resolved      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|(
name|ConstantPool
name|cp
parameter_list|)
block|{
return|return
name|super
operator|.
name|toString
argument_list|(
name|cp
argument_list|)
operator|+
literal|" "
operator|+
name|dimensions
return|;
block|}
comment|/**      * Also works for instructions whose stack effect depends on the      * constant pool entry they reference.      * @return Number of words consumed from stack by this instruction      */
annotation|@
name|Override
specifier|public
name|int
name|consumeStack
parameter_list|(
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
return|return
name|dimensions
return|;
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
literal|2
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
operator|+
literal|1
index|]
operator|=
name|ExceptionConstants
operator|.
name|NEGATIVE_ARRAY_SIZE_EXCEPTION
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
name|ILLEGAL_ACCESS_ERROR
expr_stmt|;
return|return
name|cs
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
name|visitMULTIANEWARRAY
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

