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
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|CodeException
import|;
end_import

begin_comment
comment|/**  * This class represents an exception handler, i.e., specifies the  region where  * a handler is active and an instruction where the actual handling is done.  * pool as parameters. Opposed to the JVM specification the end of the handled  * region is set to be inclusive, i.e. all instructions between start and end  * are protected including the start and end instructions (handles) themselves.  * The end of the region is automatically mapped to be exclusive when calling  * getCodeException(), i.e., there is no difference semantically.  *  * @see     MethodGen  * @see     CodeException  * @see     InstructionHandle  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|CodeExceptionGen
implements|implements
name|InstructionTargeter
implements|,
name|Cloneable
block|{
specifier|private
name|InstructionHandle
name|startPc
decl_stmt|;
specifier|private
name|InstructionHandle
name|endPc
decl_stmt|;
specifier|private
name|InstructionHandle
name|handlerPc
decl_stmt|;
specifier|private
name|ObjectType
name|catchType
decl_stmt|;
comment|/**      * Add an exception handler, i.e., specify region where a handler is active and an      * instruction where the actual handling is done.      *      * @param startPc Start of handled region (inclusive)      * @param endPc End of handled region (inclusive)      * @param handlerPc Where handling is done      * @param catchType which exception is handled, null for ANY      */
specifier|public
name|CodeExceptionGen
parameter_list|(
specifier|final
name|InstructionHandle
name|startPc
parameter_list|,
specifier|final
name|InstructionHandle
name|endPc
parameter_list|,
specifier|final
name|InstructionHandle
name|handlerPc
parameter_list|,
specifier|final
name|ObjectType
name|catchType
parameter_list|)
block|{
name|setStartPC
argument_list|(
name|startPc
argument_list|)
expr_stmt|;
name|setEndPC
argument_list|(
name|endPc
argument_list|)
expr_stmt|;
name|setHandlerPC
argument_list|(
name|handlerPc
argument_list|)
expr_stmt|;
name|this
operator|.
name|catchType
operator|=
name|catchType
expr_stmt|;
block|}
comment|/**      * Get CodeException object.<BR>      *      * This relies on that the instruction list has already been dumped      * to byte code or or that the `setPositions' methods has been      * called for the instruction list.      *      * @param cp constant pool      */
specifier|public
name|CodeException
name|getCodeException
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cp
parameter_list|)
block|{
return|return
operator|new
name|CodeException
argument_list|(
name|startPc
operator|.
name|getPosition
argument_list|()
argument_list|,
name|endPc
operator|.
name|getPosition
argument_list|()
operator|+
name|endPc
operator|.
name|getInstruction
argument_list|()
operator|.
name|getLength
argument_list|()
argument_list|,
name|handlerPc
operator|.
name|getPosition
argument_list|()
argument_list|,
name|catchType
operator|==
literal|null
condition|?
literal|0
else|:
name|cp
operator|.
name|addClass
argument_list|(
name|catchType
argument_list|)
argument_list|)
return|;
block|}
comment|/* Set start of handler      * @param startPc Start of handled region (inclusive)      */
specifier|public
name|void
name|setStartPC
parameter_list|(
specifier|final
name|InstructionHandle
name|start_pc
parameter_list|)
block|{
comment|// TODO could be package-protected?
name|BranchInstruction
operator|.
name|notifyTarget
argument_list|(
name|this
operator|.
name|startPc
argument_list|,
name|start_pc
argument_list|,
name|this
argument_list|)
expr_stmt|;
name|this
operator|.
name|startPc
operator|=
name|start_pc
expr_stmt|;
block|}
comment|/* Set end of handler      * @param endPc End of handled region (inclusive)      */
specifier|public
name|void
name|setEndPC
parameter_list|(
specifier|final
name|InstructionHandle
name|end_pc
parameter_list|)
block|{
comment|// TODO could be package-protected?
name|BranchInstruction
operator|.
name|notifyTarget
argument_list|(
name|this
operator|.
name|endPc
argument_list|,
name|end_pc
argument_list|,
name|this
argument_list|)
expr_stmt|;
name|this
operator|.
name|endPc
operator|=
name|end_pc
expr_stmt|;
block|}
comment|/* Set handler code      * @param handlerPc Start of handler      */
specifier|public
name|void
name|setHandlerPC
parameter_list|(
specifier|final
name|InstructionHandle
name|handler_pc
parameter_list|)
block|{
comment|// TODO could be package-protected?
name|BranchInstruction
operator|.
name|notifyTarget
argument_list|(
name|this
operator|.
name|handlerPc
argument_list|,
name|handler_pc
argument_list|,
name|this
argument_list|)
expr_stmt|;
name|this
operator|.
name|handlerPc
operator|=
name|handler_pc
expr_stmt|;
block|}
comment|/**      * @param old_ih old target, either start or end      * @param new_ih new target      */
annotation|@
name|Override
specifier|public
name|void
name|updateTarget
parameter_list|(
specifier|final
name|InstructionHandle
name|old_ih
parameter_list|,
specifier|final
name|InstructionHandle
name|new_ih
parameter_list|)
block|{
name|boolean
name|targeted
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|startPc
operator|==
name|old_ih
condition|)
block|{
name|targeted
operator|=
literal|true
expr_stmt|;
name|setStartPC
argument_list|(
name|new_ih
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|endPc
operator|==
name|old_ih
condition|)
block|{
name|targeted
operator|=
literal|true
expr_stmt|;
name|setEndPC
argument_list|(
name|new_ih
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|handlerPc
operator|==
name|old_ih
condition|)
block|{
name|targeted
operator|=
literal|true
expr_stmt|;
name|setHandlerPC
argument_list|(
name|new_ih
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|targeted
condition|)
block|{
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Not targeting "
operator|+
name|old_ih
operator|+
literal|", but {"
operator|+
name|startPc
operator|+
literal|", "
operator|+
name|endPc
operator|+
literal|", "
operator|+
name|handlerPc
operator|+
literal|"}"
argument_list|)
throw|;
block|}
block|}
comment|/**      * @return true, if ih is target of this handler      */
annotation|@
name|Override
specifier|public
name|boolean
name|containsTarget
parameter_list|(
specifier|final
name|InstructionHandle
name|ih
parameter_list|)
block|{
return|return
name|startPc
operator|==
name|ih
operator|||
name|endPc
operator|==
name|ih
operator|||
name|handlerPc
operator|==
name|ih
return|;
block|}
comment|/** Sets the type of the Exception to catch. Set 'null' for ANY. */
specifier|public
name|void
name|setCatchType
parameter_list|(
specifier|final
name|ObjectType
name|catchType
parameter_list|)
block|{
name|this
operator|.
name|catchType
operator|=
name|catchType
expr_stmt|;
block|}
comment|/** Gets the type of the Exception to catch, 'null' for ANY. */
specifier|public
name|ObjectType
name|getCatchType
parameter_list|()
block|{
return|return
name|catchType
return|;
block|}
comment|/** @return start of handled region (inclusive)      */
specifier|public
name|InstructionHandle
name|getStartPC
parameter_list|()
block|{
return|return
name|startPc
return|;
block|}
comment|/** @return end of handled region (inclusive)      */
specifier|public
name|InstructionHandle
name|getEndPC
parameter_list|()
block|{
return|return
name|endPc
return|;
block|}
comment|/** @return start of handler      */
specifier|public
name|InstructionHandle
name|getHandlerPC
parameter_list|()
block|{
return|return
name|handlerPc
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"CodeExceptionGen("
operator|+
name|startPc
operator|+
literal|", "
operator|+
name|endPc
operator|+
literal|", "
operator|+
name|handlerPc
operator|+
literal|")"
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|clone
parameter_list|()
block|{
try|try
block|{
return|return
name|super
operator|.
name|clone
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
specifier|final
name|CloneNotSupportedException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|Error
argument_list|(
literal|"Clone Not Supported"
argument_list|)
throw|;
comment|// never happens
block|}
block|}
block|}
end_class

end_unit

