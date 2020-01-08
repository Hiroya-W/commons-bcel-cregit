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
name|Const
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
name|ExceptionConst
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
comment|/**  * Class for INVOKEDYNAMIC. Not an instance of InvokeInstruction, since that class  * expects to be able to get the class of the method. Ignores the bootstrap  * mechanism entirely.  *  * @see  *<a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.invokedynamic">  * The invokedynamic instruction in The Java Virtual Machine Specification</a>  * @since 6.0  */
end_comment

begin_class
specifier|public
class|class
name|INVOKEDYNAMIC
extends|extends
name|InvokeInstruction
block|{
comment|/**      * Empty constructor needed for Instruction.readInstruction.      * Not to be used otherwise.      */
name|INVOKEDYNAMIC
parameter_list|()
block|{
block|}
specifier|public
name|INVOKEDYNAMIC
parameter_list|(
specifier|final
name|int
name|index
parameter_list|)
block|{
name|super
argument_list|(
name|Const
operator|.
name|INVOKEDYNAMIC
argument_list|,
name|index
argument_list|)
expr_stmt|;
block|}
comment|/**      * Dump instruction as byte code to stream out.      * @param out Output stream      */
annotation|@
name|Override
specifier|public
name|void
name|dump
parameter_list|(
specifier|final
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
name|super
operator|.
name|getOpcode
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeShort
argument_list|(
name|super
operator|.
name|getIndex
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeByte
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeByte
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
comment|/**      * Read needed data (i.e., index) from file.      */
annotation|@
name|Override
specifier|protected
name|void
name|initFromFile
parameter_list|(
specifier|final
name|ByteSequence
name|bytes
parameter_list|,
specifier|final
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
name|super
operator|.
name|setLength
argument_list|(
literal|5
argument_list|)
expr_stmt|;
name|bytes
operator|.
name|readByte
argument_list|()
expr_stmt|;
comment|// Skip 0 byte
name|bytes
operator|.
name|readByte
argument_list|()
expr_stmt|;
comment|// Skip 0 byte
block|}
comment|/**      * @return mnemonic for instruction with symbolic references resolved      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|(
specifier|final
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
return|;
block|}
annotation|@
name|Override
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
name|ExceptionConst
operator|.
name|createExceptions
argument_list|(
name|ExceptionConst
operator|.
name|EXCS
operator|.
name|EXCS_INTERFACE_METHOD_RESOLUTION
argument_list|,
name|ExceptionConst
operator|.
name|UNSATISFIED_LINK_ERROR
argument_list|,
name|ExceptionConst
operator|.
name|ABSTRACT_METHOD_ERROR
argument_list|,
name|ExceptionConst
operator|.
name|ILLEGAL_ACCESS_ERROR
argument_list|,
name|ExceptionConst
operator|.
name|INCOMPATIBLE_CLASS_CHANGE_ERROR
argument_list|)
return|;
block|}
comment|/**      * Call corresponding visitor method(s). The order is:      * Call visitor methods of implemented interfaces first, then      * call methods according to the class hierarchy in descending order,      * i.e., the most specific visitXXX() call comes last.      *      * @param v Visitor object      */
annotation|@
name|Override
specifier|public
name|void
name|accept
parameter_list|(
specifier|final
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
name|visitLoadClass
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
name|visitFieldOrMethod
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|v
operator|.
name|visitInvokeInstruction
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
comment|/**      * Override the parent method because our classname is held elsewhere.      */
annotation|@
name|Override
specifier|public
name|String
name|getClassName
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
specifier|final
name|ConstantPool
name|cp
init|=
name|cpg
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
specifier|final
name|ConstantInvokeDynamic
name|cid
init|=
operator|(
name|ConstantInvokeDynamic
operator|)
name|cp
operator|.
name|getConstant
argument_list|(
name|super
operator|.
name|getIndex
argument_list|()
argument_list|,
name|Const
operator|.
name|CONSTANT_InvokeDynamic
argument_list|)
decl_stmt|;
return|return
operator|(
operator|(
name|ConstantNameAndType
operator|)
name|cp
operator|.
name|getConstant
argument_list|(
name|cid
operator|.
name|getNameAndTypeIndex
argument_list|()
argument_list|)
operator|)
operator|.
name|getName
argument_list|(
name|cp
argument_list|)
return|;
block|}
comment|/**      * Since InvokeDynamic doesn't refer to a reference type, just return java.lang.Object,      * as that is the only type we can say for sure the reference will be.      *      * @param cpg      *            the ConstantPoolGen used to create the instruction      * @return an ObjectType for java.lang.Object      * @since 6.1      */
annotation|@
name|Override
specifier|public
name|ReferenceType
name|getReferenceType
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
return|return
operator|new
name|ObjectType
argument_list|(
name|Object
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

