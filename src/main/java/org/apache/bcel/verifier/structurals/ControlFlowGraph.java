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
name|structurals
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|generic
operator|.
name|ATHROW
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
name|generic
operator|.
name|BranchInstruction
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
name|generic
operator|.
name|GotoInstruction
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
name|generic
operator|.
name|Instruction
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
name|generic
operator|.
name|InstructionHandle
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
name|generic
operator|.
name|JsrInstruction
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
name|generic
operator|.
name|MethodGen
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
name|generic
operator|.
name|RET
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
name|generic
operator|.
name|ReturnInstruction
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
name|generic
operator|.
name|Select
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
name|verifier
operator|.
name|exc
operator|.
name|AssertionViolatedException
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
name|verifier
operator|.
name|exc
operator|.
name|StructuralCodeConstraintException
import|;
end_import

begin_comment
comment|/**  * This class represents a control flow graph of a method.  *  * @version $Id$  * @author Enver Haase  */
end_comment

begin_class
specifier|public
class|class
name|ControlFlowGraph
block|{
comment|/**      * Objects of this class represent a node in a ControlFlowGraph.      * These nodes are instructions, not basic blocks.      */
specifier|private
class|class
name|InstructionContextImpl
implements|implements
name|InstructionContext
block|{
comment|/**          * The TAG field is here for external temporary flagging, such          * as graph colouring.          *          * @see #getTag()          * @see #setTag(int)          */
specifier|private
name|int
name|TAG
decl_stmt|;
comment|/**          * The InstructionHandle this InstructionContext is wrapped around.          */
specifier|private
name|InstructionHandle
name|instruction
decl_stmt|;
comment|/**          * The 'incoming' execution Frames.          */
specifier|private
name|Map
argument_list|<
name|InstructionContext
argument_list|,
name|Frame
argument_list|>
name|inFrames
decl_stmt|;
comment|// key: the last-executed JSR
comment|/**          * The 'outgoing' execution Frames.          */
specifier|private
name|Map
argument_list|<
name|InstructionContext
argument_list|,
name|Frame
argument_list|>
name|outFrames
decl_stmt|;
comment|// key: the last-executed JSR
comment|/**          * The 'execution predecessors' - a list of type InstructionContext           * of those instances that have been execute()d before in that order.          */
specifier|private
name|List
argument_list|<
name|InstructionContext
argument_list|>
name|executionPredecessors
init|=
literal|null
decl_stmt|;
comment|// Type: InstructionContext
comment|/**          * Creates an InstructionHandleImpl object from an InstructionHandle.          * Creation of one per InstructionHandle suffices. Don't create more.          */
specifier|public
name|InstructionContextImpl
parameter_list|(
name|InstructionHandle
name|inst
parameter_list|)
block|{
if|if
condition|(
name|inst
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|AssertionViolatedException
argument_list|(
literal|"Cannot instantiate InstructionContextImpl from NULL."
argument_list|)
throw|;
block|}
name|instruction
operator|=
name|inst
expr_stmt|;
name|inFrames
operator|=
operator|new
name|HashMap
argument_list|<
name|InstructionContext
argument_list|,
name|Frame
argument_list|>
argument_list|()
expr_stmt|;
name|outFrames
operator|=
operator|new
name|HashMap
argument_list|<
name|InstructionContext
argument_list|,
name|Frame
argument_list|>
argument_list|()
expr_stmt|;
block|}
comment|/* Satisfies InstructionContext.getTag(). */
specifier|public
name|int
name|getTag
parameter_list|()
block|{
return|return
name|TAG
return|;
block|}
comment|/* Satisfies InstructionContext.setTag(int). */
specifier|public
name|void
name|setTag
parameter_list|(
name|int
name|tag
parameter_list|)
block|{
name|TAG
operator|=
name|tag
expr_stmt|;
block|}
comment|/**          * Returns the exception handlers of this instruction.          */
specifier|public
name|ExceptionHandler
index|[]
name|getExceptionHandlers
parameter_list|()
block|{
return|return
name|exceptionhandlers
operator|.
name|getExceptionHandlers
argument_list|(
name|getInstruction
argument_list|()
argument_list|)
return|;
block|}
comment|/**          * Returns a clone of the "outgoing" frame situation with respect to the given ExecutionChain.          */
specifier|public
name|Frame
name|getOutFrame
parameter_list|(
name|ArrayList
argument_list|<
name|InstructionContext
argument_list|>
name|execChain
parameter_list|)
block|{
name|executionPredecessors
operator|=
name|execChain
expr_stmt|;
name|Frame
name|org
decl_stmt|;
name|InstructionContext
name|jsr
init|=
name|lastExecutionJSR
argument_list|()
decl_stmt|;
name|org
operator|=
name|outFrames
operator|.
name|get
argument_list|(
name|jsr
argument_list|)
expr_stmt|;
if|if
condition|(
name|org
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|AssertionViolatedException
argument_list|(
literal|"outFrame not set! This:\n"
operator|+
name|this
operator|+
literal|"\nExecutionChain: "
operator|+
name|getExecutionChain
argument_list|()
operator|+
literal|"\nOutFrames: '"
operator|+
name|outFrames
operator|+
literal|"'."
argument_list|)
throw|;
block|}
return|return
name|org
operator|.
name|getClone
argument_list|()
return|;
block|}
specifier|public
name|Frame
name|getInFrame
parameter_list|()
block|{
name|Frame
name|org
decl_stmt|;
name|InstructionContext
name|jsr
init|=
name|lastExecutionJSR
argument_list|()
decl_stmt|;
name|org
operator|=
name|inFrames
operator|.
name|get
argument_list|(
name|jsr
argument_list|)
expr_stmt|;
if|if
condition|(
name|org
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|AssertionViolatedException
argument_list|(
literal|"inFrame not set! This:\n"
operator|+
name|this
operator|+
literal|"\nInFrames: '"
operator|+
name|inFrames
operator|+
literal|"'."
argument_list|)
throw|;
block|}
return|return
name|org
operator|.
name|getClone
argument_list|()
return|;
block|}
comment|/**          * "Merges in" (vmspec2, page 146) the "incoming" frame situation;          * executes the instructions symbolically          * and therefore calculates the "outgoing" frame situation.          * Returns: True iff the "incoming" frame situation changed after          * merging with "inFrame".          * The execPreds ArrayList must contain the InstructionContext          * objects executed so far in the correct order. This is just          * one execution path [out of many]. This is needed to correctly          * "merge" in the special case of a RET's successor.          *<B>The InstConstraintVisitor and ExecutionVisitor instances          * must be set up correctly.</B>          * @return true - if and only if the "outgoing" frame situation          * changed from the one before execute()ing.          */
specifier|public
name|boolean
name|execute
parameter_list|(
name|Frame
name|inFrame
parameter_list|,
name|ArrayList
argument_list|<
name|InstructionContext
argument_list|>
name|execPreds
parameter_list|,
name|InstConstraintVisitor
name|icv
parameter_list|,
name|ExecutionVisitor
name|ev
parameter_list|)
block|{
name|executionPredecessors
operator|=
operator|(
name|List
argument_list|<
name|InstructionContext
argument_list|>
operator|)
name|execPreds
operator|.
name|clone
argument_list|()
expr_stmt|;
comment|//sanity check
if|if
condition|(
operator|(
name|lastExecutionJSR
argument_list|()
operator|==
literal|null
operator|)
operator|&&
operator|(
name|subroutines
operator|.
name|subroutineOf
argument_list|(
name|getInstruction
argument_list|()
argument_list|)
operator|!=
name|subroutines
operator|.
name|getTopLevel
argument_list|()
operator|)
condition|)
block|{
throw|throw
operator|new
name|AssertionViolatedException
argument_list|(
literal|"Huh?! Am I '"
operator|+
name|this
operator|+
literal|"' part of a subroutine or not?"
argument_list|)
throw|;
block|}
if|if
condition|(
operator|(
name|lastExecutionJSR
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|subroutines
operator|.
name|subroutineOf
argument_list|(
name|getInstruction
argument_list|()
argument_list|)
operator|==
name|subroutines
operator|.
name|getTopLevel
argument_list|()
operator|)
condition|)
block|{
throw|throw
operator|new
name|AssertionViolatedException
argument_list|(
literal|"Huh?! Am I '"
operator|+
name|this
operator|+
literal|"' part of a subroutine or not?"
argument_list|)
throw|;
block|}
name|Frame
name|inF
init|=
name|inFrames
operator|.
name|get
argument_list|(
name|lastExecutionJSR
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|inF
operator|==
literal|null
condition|)
block|{
comment|// no incoming frame was set, so set it.
name|inFrames
operator|.
name|put
argument_list|(
name|lastExecutionJSR
argument_list|()
argument_list|,
name|inFrame
argument_list|)
expr_stmt|;
name|inF
operator|=
name|inFrame
expr_stmt|;
block|}
else|else
block|{
comment|// if there was an "old" inFrame
if|if
condition|(
name|inF
operator|.
name|equals
argument_list|(
name|inFrame
argument_list|)
condition|)
block|{
comment|//shortcut: no need to merge equal frames.
return|return
literal|false
return|;
block|}
if|if
condition|(
operator|!
name|mergeInFrames
argument_list|(
name|inFrame
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
comment|// Now we're sure the inFrame has changed!
comment|// new inFrame is already merged in, see above.
name|Frame
name|workingFrame
init|=
name|inF
operator|.
name|getClone
argument_list|()
decl_stmt|;
try|try
block|{
comment|// This verifies the InstructionConstraint for the current
comment|// instruction, but does not modify the workingFrame object.
comment|//InstConstraintVisitor icv = InstConstraintVisitor.getInstance(VerifierFactory.getVerifier(method_gen.getClassName()));
name|icv
operator|.
name|setFrame
argument_list|(
name|workingFrame
argument_list|)
expr_stmt|;
name|getInstruction
argument_list|()
operator|.
name|accept
argument_list|(
name|icv
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|StructuralCodeConstraintException
name|ce
parameter_list|)
block|{
name|ce
operator|.
name|extendMessage
argument_list|(
literal|""
argument_list|,
literal|"\nInstructionHandle: "
operator|+
name|getInstruction
argument_list|()
operator|+
literal|"\n"
argument_list|)
expr_stmt|;
name|ce
operator|.
name|extendMessage
argument_list|(
literal|""
argument_list|,
literal|"\nExecution Frame:\n"
operator|+
name|workingFrame
argument_list|)
expr_stmt|;
name|extendMessageWithFlow
argument_list|(
name|ce
argument_list|)
expr_stmt|;
throw|throw
name|ce
throw|;
block|}
comment|// This executes the Instruction.
comment|// Therefore the workingFrame object is modified.
comment|//ExecutionVisitor ev = ExecutionVisitor.getInstance(VerifierFactory.getVerifier(method_gen.getClassName()));
name|ev
operator|.
name|setFrame
argument_list|(
name|workingFrame
argument_list|)
expr_stmt|;
name|getInstruction
argument_list|()
operator|.
name|accept
argument_list|(
name|ev
argument_list|)
expr_stmt|;
comment|//getInstruction().accept(ExecutionVisitor.withFrame(workingFrame));
name|outFrames
operator|.
name|put
argument_list|(
name|lastExecutionJSR
argument_list|()
argument_list|,
name|workingFrame
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
comment|// new inFrame was different from old inFrame so merging them
comment|// yielded a different this.inFrame.
block|}
comment|/**          * Returns a simple String representation of this InstructionContext.          */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
comment|//TODO: Put information in the brackets, e.g.
comment|//      Is this an ExceptionHandler? Is this a RET? Is this the start of
comment|//      a subroutine?
name|String
name|ret
init|=
name|getInstruction
argument_list|()
operator|.
name|toString
argument_list|(
literal|false
argument_list|)
operator|+
literal|"\t[InstructionContext]"
decl_stmt|;
return|return
name|ret
return|;
block|}
comment|/**          * Does the actual merging (vmspec2, page 146).          * Returns true IFF this.inFrame was changed in course of merging with inFrame.          */
specifier|private
name|boolean
name|mergeInFrames
parameter_list|(
name|Frame
name|inFrame
parameter_list|)
block|{
comment|// TODO: Can be performance-improved.
name|Frame
name|inF
init|=
name|inFrames
operator|.
name|get
argument_list|(
name|lastExecutionJSR
argument_list|()
argument_list|)
decl_stmt|;
name|OperandStack
name|oldstack
init|=
name|inF
operator|.
name|getStack
argument_list|()
operator|.
name|getClone
argument_list|()
decl_stmt|;
name|LocalVariables
name|oldlocals
init|=
name|inF
operator|.
name|getLocals
argument_list|()
operator|.
name|getClone
argument_list|()
decl_stmt|;
try|try
block|{
name|inF
operator|.
name|getStack
argument_list|()
operator|.
name|merge
argument_list|(
name|inFrame
operator|.
name|getStack
argument_list|()
argument_list|)
expr_stmt|;
name|inF
operator|.
name|getLocals
argument_list|()
operator|.
name|merge
argument_list|(
name|inFrame
operator|.
name|getLocals
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|StructuralCodeConstraintException
name|sce
parameter_list|)
block|{
name|extendMessageWithFlow
argument_list|(
name|sce
argument_list|)
expr_stmt|;
throw|throw
name|sce
throw|;
block|}
return|return
operator|!
operator|(
name|oldstack
operator|.
name|equals
argument_list|(
name|inF
operator|.
name|getStack
argument_list|()
argument_list|)
operator|&&
name|oldlocals
operator|.
name|equals
argument_list|(
name|inF
operator|.
name|getLocals
argument_list|()
argument_list|)
operator|)
return|;
block|}
comment|/**          * Returns the control flow execution chain. This is built          * while execute(Frame, ArrayList)-ing the code represented          * by the surrounding ControlFlowGraph.          */
specifier|private
name|String
name|getExecutionChain
parameter_list|()
block|{
name|String
name|s
init|=
name|this
operator|.
name|toString
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|executionPredecessors
operator|.
name|size
argument_list|()
operator|-
literal|1
init|;
name|i
operator|>=
literal|0
condition|;
name|i
operator|--
control|)
block|{
name|s
operator|=
name|executionPredecessors
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|+
literal|"\n"
operator|+
name|s
expr_stmt|;
block|}
return|return
name|s
return|;
block|}
comment|/**          * Extends the StructuralCodeConstraintException ("e") object with an at-the-end-extended message.          * This extended message will then reflect the execution flow needed to get to the constraint          * violation that triggered the throwing of the "e" object.          */
specifier|private
name|void
name|extendMessageWithFlow
parameter_list|(
name|StructuralCodeConstraintException
name|e
parameter_list|)
block|{
name|String
name|s
init|=
literal|"Execution flow:\n"
decl_stmt|;
name|e
operator|.
name|extendMessage
argument_list|(
literal|""
argument_list|,
name|s
operator|+
name|getExecutionChain
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/*          * Fulfils the contract of InstructionContext.getInstruction().          */
specifier|public
name|InstructionHandle
name|getInstruction
parameter_list|()
block|{
return|return
name|instruction
return|;
block|}
comment|/**          * Returns the InstructionContextImpl with an JSR/JSR_W          * that was last in the ExecutionChain, without          * a corresponding RET, i.e.          * we were called by this one.          * Returns null if we were called from the top level.          */
specifier|private
name|InstructionContextImpl
name|lastExecutionJSR
parameter_list|()
block|{
name|int
name|size
init|=
name|executionPredecessors
operator|.
name|size
argument_list|()
decl_stmt|;
name|int
name|retcount
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|size
operator|-
literal|1
init|;
name|i
operator|>=
literal|0
condition|;
name|i
operator|--
control|)
block|{
name|InstructionContextImpl
name|current
init|=
operator|(
name|InstructionContextImpl
operator|)
operator|(
name|executionPredecessors
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|)
decl_stmt|;
name|Instruction
name|currentlast
init|=
name|current
operator|.
name|getInstruction
argument_list|()
operator|.
name|getInstruction
argument_list|()
decl_stmt|;
if|if
condition|(
name|currentlast
operator|instanceof
name|RET
condition|)
block|{
name|retcount
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|currentlast
operator|instanceof
name|JsrInstruction
condition|)
block|{
name|retcount
operator|--
expr_stmt|;
if|if
condition|(
name|retcount
operator|==
operator|-
literal|1
condition|)
block|{
return|return
name|current
return|;
block|}
block|}
block|}
return|return
literal|null
return|;
block|}
comment|/* Satisfies InstructionContext.getSuccessors(). */
specifier|public
name|InstructionContext
index|[]
name|getSuccessors
parameter_list|()
block|{
return|return
name|contextsOf
argument_list|(
name|_getSuccessors
argument_list|()
argument_list|)
return|;
block|}
comment|/**          * A utility method that calculates the successors of a given InstructionHandle          * That means, a RET does have successors as defined here.          * A JsrInstruction has its target as its successor          * (opposed to its physical successor) as defined here.          */
comment|// TODO: implement caching!
specifier|private
name|InstructionHandle
index|[]
name|_getSuccessors
parameter_list|()
block|{
specifier|final
name|InstructionHandle
index|[]
name|empty
init|=
operator|new
name|InstructionHandle
index|[
literal|0
index|]
decl_stmt|;
specifier|final
name|InstructionHandle
index|[]
name|single
init|=
operator|new
name|InstructionHandle
index|[
literal|1
index|]
decl_stmt|;
name|Instruction
name|inst
init|=
name|getInstruction
argument_list|()
operator|.
name|getInstruction
argument_list|()
decl_stmt|;
if|if
condition|(
name|inst
operator|instanceof
name|RET
condition|)
block|{
name|Subroutine
name|s
init|=
name|subroutines
operator|.
name|subroutineOf
argument_list|(
name|getInstruction
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|s
operator|==
literal|null
condition|)
block|{
comment|//return empty; // RET in dead code. "empty" would be the correct answer, but we know something about the surrounding project...
throw|throw
operator|new
name|AssertionViolatedException
argument_list|(
literal|"Asking for successors of a RET in dead code?!"
argument_list|)
throw|;
block|}
comment|//TODO: remove. Only JustIce must not use it, but foreign users of the ControlFlowGraph
comment|//      will want it. Thanks Johannes Wust.
comment|//throw new AssertionViolatedException("DID YOU REALLY WANT TO ASK FOR RET'S SUCCS?");
name|InstructionHandle
index|[]
name|jsrs
init|=
name|s
operator|.
name|getEnteringJsrInstructions
argument_list|()
decl_stmt|;
name|InstructionHandle
index|[]
name|ret
init|=
operator|new
name|InstructionHandle
index|[
name|jsrs
operator|.
name|length
index|]
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|jsrs
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|ret
index|[
name|i
index|]
operator|=
name|jsrs
index|[
name|i
index|]
operator|.
name|getNext
argument_list|()
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
comment|// Terminates method normally.
if|if
condition|(
name|inst
operator|instanceof
name|ReturnInstruction
condition|)
block|{
return|return
name|empty
return|;
block|}
comment|// Terminates method abnormally, because JustIce mandates
comment|// subroutines not to be protected by exception handlers.
if|if
condition|(
name|inst
operator|instanceof
name|ATHROW
condition|)
block|{
return|return
name|empty
return|;
block|}
comment|// See method comment.
if|if
condition|(
name|inst
operator|instanceof
name|JsrInstruction
condition|)
block|{
name|single
index|[
literal|0
index|]
operator|=
operator|(
operator|(
name|JsrInstruction
operator|)
name|inst
operator|)
operator|.
name|getTarget
argument_list|()
expr_stmt|;
return|return
name|single
return|;
block|}
if|if
condition|(
name|inst
operator|instanceof
name|GotoInstruction
condition|)
block|{
name|single
index|[
literal|0
index|]
operator|=
operator|(
operator|(
name|GotoInstruction
operator|)
name|inst
operator|)
operator|.
name|getTarget
argument_list|()
expr_stmt|;
return|return
name|single
return|;
block|}
if|if
condition|(
name|inst
operator|instanceof
name|BranchInstruction
condition|)
block|{
if|if
condition|(
name|inst
operator|instanceof
name|Select
condition|)
block|{
comment|// BCEL's getTargets() returns only the non-default targets,
comment|// thanks to Eli Tilevich for reporting.
name|InstructionHandle
index|[]
name|matchTargets
init|=
operator|(
operator|(
name|Select
operator|)
name|inst
operator|)
operator|.
name|getTargets
argument_list|()
decl_stmt|;
name|InstructionHandle
index|[]
name|ret
init|=
operator|new
name|InstructionHandle
index|[
name|matchTargets
operator|.
name|length
operator|+
literal|1
index|]
decl_stmt|;
name|ret
index|[
literal|0
index|]
operator|=
operator|(
operator|(
name|Select
operator|)
name|inst
operator|)
operator|.
name|getTarget
argument_list|()
expr_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|matchTargets
argument_list|,
literal|0
argument_list|,
name|ret
argument_list|,
literal|1
argument_list|,
name|matchTargets
operator|.
name|length
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|final
name|InstructionHandle
index|[]
name|pair
init|=
operator|new
name|InstructionHandle
index|[
literal|2
index|]
decl_stmt|;
name|pair
index|[
literal|0
index|]
operator|=
name|getInstruction
argument_list|()
operator|.
name|getNext
argument_list|()
expr_stmt|;
name|pair
index|[
literal|1
index|]
operator|=
operator|(
operator|(
name|BranchInstruction
operator|)
name|inst
operator|)
operator|.
name|getTarget
argument_list|()
expr_stmt|;
return|return
name|pair
return|;
block|}
comment|// default case: Fall through.
name|single
index|[
literal|0
index|]
operator|=
name|getInstruction
argument_list|()
operator|.
name|getNext
argument_list|()
expr_stmt|;
return|return
name|single
return|;
block|}
block|}
comment|// End Inner InstructionContextImpl Class.
comment|///** The MethodGen object we're working on. */
comment|//private final MethodGen method_gen;
comment|/** The Subroutines object for the method whose control flow is represented by this ControlFlowGraph. */
specifier|private
specifier|final
name|Subroutines
name|subroutines
decl_stmt|;
comment|/** The ExceptionHandlers object for the method whose control flow is represented by this ControlFlowGraph. */
specifier|private
specifier|final
name|ExceptionHandlers
name|exceptionhandlers
decl_stmt|;
comment|/** All InstructionContext instances of this ControlFlowGraph. */
specifier|private
name|Map
argument_list|<
name|InstructionHandle
argument_list|,
name|InstructionContext
argument_list|>
name|instructionContexts
init|=
operator|new
name|HashMap
argument_list|<
name|InstructionHandle
argument_list|,
name|InstructionContext
argument_list|>
argument_list|()
decl_stmt|;
comment|//keys: InstructionHandle, values: InstructionContextImpl
comment|/**       * A Control Flow Graph.      */
specifier|public
name|ControlFlowGraph
parameter_list|(
name|MethodGen
name|method_gen
parameter_list|)
block|{
name|subroutines
operator|=
operator|new
name|Subroutines
argument_list|(
name|method_gen
argument_list|)
expr_stmt|;
name|exceptionhandlers
operator|=
operator|new
name|ExceptionHandlers
argument_list|(
name|method_gen
argument_list|)
expr_stmt|;
name|InstructionHandle
index|[]
name|instructionhandles
init|=
name|method_gen
operator|.
name|getInstructionList
argument_list|()
operator|.
name|getInstructionHandles
argument_list|()
decl_stmt|;
for|for
control|(
name|InstructionHandle
name|instructionhandle
range|:
name|instructionhandles
control|)
block|{
name|instructionContexts
operator|.
name|put
argument_list|(
name|instructionhandle
argument_list|,
operator|new
name|InstructionContextImpl
argument_list|(
name|instructionhandle
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|//this.method_gen = method_gen;
block|}
comment|/**      * Returns the InstructionContext of a given instruction.      */
specifier|public
name|InstructionContext
name|contextOf
parameter_list|(
name|InstructionHandle
name|inst
parameter_list|)
block|{
name|InstructionContext
name|ic
init|=
name|instructionContexts
operator|.
name|get
argument_list|(
name|inst
argument_list|)
decl_stmt|;
if|if
condition|(
name|ic
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|AssertionViolatedException
argument_list|(
literal|"InstructionContext requested for an InstructionHandle that's not known!"
argument_list|)
throw|;
block|}
return|return
name|ic
return|;
block|}
comment|/**      * Returns the InstructionContext[] of a given InstructionHandle[],      * in a naturally ordered manner.      */
specifier|public
name|InstructionContext
index|[]
name|contextsOf
parameter_list|(
name|InstructionHandle
index|[]
name|insts
parameter_list|)
block|{
name|InstructionContext
index|[]
name|ret
init|=
operator|new
name|InstructionContext
index|[
name|insts
operator|.
name|length
index|]
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|insts
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|ret
index|[
name|i
index|]
operator|=
name|contextOf
argument_list|(
name|insts
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
comment|/**      * Returns an InstructionContext[] with all the InstructionContext instances      * for the method whose control flow is represented by this ControlFlowGraph      *<B>(NOT ORDERED!)</B>.      */
specifier|public
name|InstructionContext
index|[]
name|getInstructionContexts
parameter_list|()
block|{
name|InstructionContext
index|[]
name|ret
init|=
operator|new
name|InstructionContext
index|[
name|instructionContexts
operator|.
name|values
argument_list|()
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
return|return
name|instructionContexts
operator|.
name|values
argument_list|()
operator|.
name|toArray
argument_list|(
name|ret
argument_list|)
return|;
block|}
comment|/**      * Returns true, if and only if the said instruction is not reachable; that means,      * if it is not part of this ControlFlowGraph.      */
specifier|public
name|boolean
name|isDead
parameter_list|(
name|InstructionHandle
name|i
parameter_list|)
block|{
return|return
name|subroutines
operator|.
name|subroutineOf
argument_list|(
name|i
argument_list|)
operator|==
literal|null
return|;
block|}
block|}
end_class

end_unit

