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
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|StringTokenizer
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
name|Constants
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
name|Constant
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
name|ConstantInvokeDynamic
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
name|ConstantNameAndType
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
comment|/**  * Class for INVOKEDYNAMIC. Not an instance of InvokeInstruction, since that class  * expects to be able to get the class of the method. Ignores the bootstrap  * mechanism entirely.  *  * @version $Id: InvokeInstruction.java 1152072 2011-07-29 01:54:05Z dbrosius $  * @author  Bill Pugh  */
end_comment

begin_class
specifier|public
class|class
name|INVOKEDYNAMIC
extends|extends
name|NameSignatureInstruction
implements|implements
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
literal|1L
decl_stmt|;
comment|/**      * Empty constructor needed for the Class.newInstance() statement in      * Instruction.readInstruction(). Not to be used otherwise.      */
name|INVOKEDYNAMIC
parameter_list|()
block|{
block|}
comment|/**      * @param index to constant pool      */
specifier|public
name|INVOKEDYNAMIC
parameter_list|(
name|short
name|opcode
parameter_list|,
name|int
name|index
parameter_list|)
block|{
name|super
argument_list|(
name|opcode
argument_list|,
name|index
argument_list|)
expr_stmt|;
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
name|Constant
name|c
init|=
name|cp
operator|.
name|getConstant
argument_list|(
name|index
argument_list|)
decl_stmt|;
name|StringTokenizer
name|tok
init|=
operator|new
name|StringTokenizer
argument_list|(
name|cp
operator|.
name|constantToString
argument_list|(
name|c
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|Constants
operator|.
name|OPCODE_NAMES
index|[
name|opcode
index|]
operator|+
literal|" "
operator|+
name|tok
operator|.
name|nextToken
argument_list|()
operator|.
name|replace
argument_list|(
literal|'.'
argument_list|,
literal|'/'
argument_list|)
operator|+
name|tok
operator|.
name|nextToken
argument_list|()
return|;
block|}
comment|/**       * Get the ConstantInvokeDynamic associated with this instruction      */
specifier|public
name|ConstantInvokeDynamic
name|getInvokeDynamic
parameter_list|(
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
name|ConstantPool
name|cp
init|=
name|cpg
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
return|return
operator|(
name|ConstantInvokeDynamic
operator|)
name|cp
operator|.
name|getConstant
argument_list|(
name|index
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|ConstantNameAndType
name|getNameAndType
parameter_list|(
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
name|ConstantPool
name|cp
init|=
name|cpg
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
name|ConstantInvokeDynamic
name|id
init|=
name|getInvokeDynamic
argument_list|(
name|cpg
argument_list|)
decl_stmt|;
return|return
operator|(
name|ConstantNameAndType
operator|)
name|cp
operator|.
name|getConstant
argument_list|(
name|id
operator|.
name|getNameAndTypeIndex
argument_list|()
argument_list|)
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
name|String
name|signature
init|=
name|getSignature
argument_list|(
name|cpg
argument_list|)
decl_stmt|;
return|return
name|Type
operator|.
name|getArgumentTypesSize
argument_list|(
name|signature
argument_list|)
return|;
block|}
comment|/**      * Also works for instructions whose stack effect depends on the      * constant pool entry they reference.      * @return Number of words produced onto stack by this instruction      */
annotation|@
name|Override
specifier|public
name|int
name|produceStack
parameter_list|(
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
name|String
name|signature
init|=
name|getSignature
argument_list|(
name|cpg
argument_list|)
decl_stmt|;
return|return
name|Type
operator|.
name|getReturnTypeSize
argument_list|(
name|signature
argument_list|)
return|;
block|}
comment|/** @return return type of referenced method.      */
annotation|@
name|Override
specifier|public
name|Type
name|getType
parameter_list|(
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
return|return
name|getReturnType
argument_list|(
name|cpg
argument_list|)
return|;
block|}
comment|/** @return name of referenced method.      */
specifier|public
name|String
name|getMethodName
parameter_list|(
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
return|return
name|getName
argument_list|(
name|cpg
argument_list|)
return|;
block|}
comment|/** @return return type of referenced method.      */
specifier|public
name|Type
name|getReturnType
parameter_list|(
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
return|return
name|Type
operator|.
name|getReturnType
argument_list|(
name|getSignature
argument_list|(
name|cpg
argument_list|)
argument_list|)
return|;
block|}
comment|/** @return argument types of referenced method.      */
specifier|public
name|Type
index|[]
name|getArgumentTypes
parameter_list|(
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
return|return
name|Type
operator|.
name|getArgumentTypes
argument_list|(
name|getSignature
argument_list|(
name|cpg
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Read needed data (i.e., index) from file.      */
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
name|length
operator|=
literal|5
expr_stmt|;
name|bytes
operator|.
name|readUnsignedShort
argument_list|()
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
literal|4
operator|+
name|ExceptionConstants
operator|.
name|EXCS_INTERFACE_METHOD_RESOLUTION
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
name|EXCS_INTERFACE_METHOD_RESOLUTION
argument_list|,
literal|0
argument_list|,
name|cs
argument_list|,
literal|0
argument_list|,
name|ExceptionConstants
operator|.
name|EXCS_INTERFACE_METHOD_RESOLUTION
operator|.
name|length
argument_list|)
expr_stmt|;
name|cs
index|[
name|ExceptionConstants
operator|.
name|EXCS_INTERFACE_METHOD_RESOLUTION
operator|.
name|length
operator|+
literal|3
index|]
operator|=
name|ExceptionConstants
operator|.
name|INCOMPATIBLE_CLASS_CHANGE_ERROR
expr_stmt|;
name|cs
index|[
name|ExceptionConstants
operator|.
name|EXCS_INTERFACE_METHOD_RESOLUTION
operator|.
name|length
operator|+
literal|2
index|]
operator|=
name|ExceptionConstants
operator|.
name|ILLEGAL_ACCESS_ERROR
expr_stmt|;
name|cs
index|[
name|ExceptionConstants
operator|.
name|EXCS_INTERFACE_METHOD_RESOLUTION
operator|.
name|length
operator|+
literal|1
index|]
operator|=
name|ExceptionConstants
operator|.
name|ABSTRACT_METHOD_ERROR
expr_stmt|;
name|cs
index|[
name|ExceptionConstants
operator|.
name|EXCS_INTERFACE_METHOD_RESOLUTION
operator|.
name|length
index|]
operator|=
name|ExceptionConstants
operator|.
name|UNSATISFIED_LINK_ERROR
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
name|visitStackConsumer
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
name|visitCPInstruction
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|v
operator|.
name|visitNameSignatureInstruction
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|v
operator|.
name|visitINVOKEDYNAMIC
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

