begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright  2000-2004 The Apache Software Foundation  *  *  Licensed under the Apache License, Version 2.0 (the "License");   *  you may not use this file except in compliance with the License.  *  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.   *  */
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
name|io
operator|.
name|PrintWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringWriter
import|;
end_import

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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Random
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
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
name|Repository
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
name|JavaClass
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
name|Method
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
name|ConstantPoolGen
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
name|ObjectType
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
name|ReturnaddressType
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
name|Type
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
name|PassVerifier
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
name|VerificationResult
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
name|Verifier
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
name|VerifierConstraintViolatedException
import|;
end_import

begin_comment
comment|/**  * This PassVerifier verifies a method of class file according to pass 3,  * so-called structural verification as described in The Java Virtual Machine  * Specification, 2nd edition.  * More detailed information is to be found at the do_verify() method's  * documentation.   *  * @version $Id$  * @author Enver Haase  * @see #do_verify()  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|Pass3bVerifier
extends|extends
name|PassVerifier
block|{
comment|/* TODO:	Throughout pass 3b, upper halves of LONG and DOUBLE 						are represented by Type.UNKNOWN. This should be changed 						in favour of LONG_Upper and DOUBLE_Upper as in pass 2. */
comment|/** 	 * An InstructionContextQueue is a utility class that holds 	 * (InstructionContext, ArrayList) pairs in a Queue data structure. 	 * This is used to hold information about InstructionContext objects 	 * externally --- i.e. that information is not saved inside the 	 * InstructionContext object itself. This is useful to save the 	 * execution path of the symbolic execution of the 	 * Pass3bVerifier - this is not information 	 * that belongs into the InstructionContext object itself. 	 * Only at "execute()"ing 	 * time, an InstructionContext object will get the current information 	 * we have about its symbolic execution predecessors. 	 */
specifier|private
specifier|static
specifier|final
class|class
name|InstructionContextQueue
block|{
specifier|private
name|List
name|ics
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
comment|// Type: InstructionContext
specifier|private
name|List
name|ecs
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
comment|// Type: ArrayList (of InstructionContext)
specifier|public
name|void
name|add
parameter_list|(
name|InstructionContext
name|ic
parameter_list|,
name|ArrayList
name|executionChain
parameter_list|)
block|{
name|ics
operator|.
name|add
argument_list|(
name|ic
argument_list|)
expr_stmt|;
name|ecs
operator|.
name|add
argument_list|(
name|executionChain
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isEmpty
parameter_list|()
block|{
return|return
name|ics
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|void
name|remove
parameter_list|()
block|{
name|this
operator|.
name|remove
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|remove
parameter_list|(
name|int
name|i
parameter_list|)
block|{
name|ics
operator|.
name|remove
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|ecs
operator|.
name|remove
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
specifier|public
name|InstructionContext
name|getIC
parameter_list|(
name|int
name|i
parameter_list|)
block|{
return|return
operator|(
name|InstructionContext
operator|)
name|ics
operator|.
name|get
argument_list|(
name|i
argument_list|)
return|;
block|}
specifier|public
name|ArrayList
name|getEC
parameter_list|(
name|int
name|i
parameter_list|)
block|{
return|return
operator|(
name|ArrayList
operator|)
name|ecs
operator|.
name|get
argument_list|(
name|i
argument_list|)
return|;
block|}
specifier|public
name|int
name|size
parameter_list|()
block|{
return|return
name|ics
operator|.
name|size
argument_list|()
return|;
block|}
block|}
comment|// end Inner Class InstructionContextQueue
comment|/** In DEBUG mode, the verification algorithm is not randomized. */
specifier|private
specifier|static
specifier|final
name|boolean
name|DEBUG
init|=
literal|true
decl_stmt|;
comment|/** The Verifier that created this. */
specifier|private
name|Verifier
name|myOwner
decl_stmt|;
comment|/** The method number to verify. */
specifier|private
name|int
name|method_no
decl_stmt|;
comment|/** 	 * This class should only be instantiated by a Verifier. 	 * 	 * @see org.apache.bcel.verifier.Verifier 	 */
specifier|public
name|Pass3bVerifier
parameter_list|(
name|Verifier
name|owner
parameter_list|,
name|int
name|method_no
parameter_list|)
block|{
name|myOwner
operator|=
name|owner
expr_stmt|;
name|this
operator|.
name|method_no
operator|=
name|method_no
expr_stmt|;
block|}
comment|/** 	 * Whenever the outgoing frame 	 * situation of an InstructionContext changes, all its successors are 	 * put [back] into the queue [as if they were unvisited].    * The proof of termination is about the existence of a    * fix point of frame merging. 	 */
specifier|private
name|void
name|circulationPump
parameter_list|(
name|ControlFlowGraph
name|cfg
parameter_list|,
name|InstructionContext
name|start
parameter_list|,
name|Frame
name|vanillaFrame
parameter_list|,
name|InstConstraintVisitor
name|icv
parameter_list|,
name|ExecutionVisitor
name|ev
parameter_list|)
block|{
specifier|final
name|Random
name|random
init|=
operator|new
name|Random
argument_list|()
decl_stmt|;
name|InstructionContextQueue
name|icq
init|=
operator|new
name|InstructionContextQueue
argument_list|()
decl_stmt|;
name|start
operator|.
name|execute
argument_list|(
name|vanillaFrame
argument_list|,
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|icv
argument_list|,
name|ev
argument_list|)
expr_stmt|;
comment|// new ArrayList()<=>	no Instruction was executed before
comment|//									=> Top-Level routine (no jsr call before)
name|icq
operator|.
name|add
argument_list|(
name|start
argument_list|,
operator|new
name|ArrayList
argument_list|()
argument_list|)
expr_stmt|;
comment|// LOOP!
while|while
condition|(
operator|!
name|icq
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|InstructionContext
name|u
decl_stmt|;
name|ArrayList
name|ec
decl_stmt|;
if|if
condition|(
operator|!
name|DEBUG
condition|)
block|{
name|int
name|r
init|=
name|random
operator|.
name|nextInt
argument_list|(
name|icq
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
name|u
operator|=
name|icq
operator|.
name|getIC
argument_list|(
name|r
argument_list|)
expr_stmt|;
name|ec
operator|=
name|icq
operator|.
name|getEC
argument_list|(
name|r
argument_list|)
expr_stmt|;
name|icq
operator|.
name|remove
argument_list|(
name|r
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|u
operator|=
name|icq
operator|.
name|getIC
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|ec
operator|=
name|icq
operator|.
name|getEC
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|icq
operator|.
name|remove
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
name|ArrayList
name|oldchain
init|=
operator|(
name|ArrayList
operator|)
operator|(
name|ec
operator|.
name|clone
argument_list|()
operator|)
decl_stmt|;
name|ArrayList
name|newchain
init|=
operator|(
name|ArrayList
operator|)
operator|(
name|ec
operator|.
name|clone
argument_list|()
operator|)
decl_stmt|;
name|newchain
operator|.
name|add
argument_list|(
name|u
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|u
operator|.
name|getInstruction
argument_list|()
operator|.
name|getInstruction
argument_list|()
operator|)
operator|instanceof
name|RET
condition|)
block|{
comment|//System.err.println(u);
comment|// We can only follow _one_ successor, the one after the
comment|// JSR that was recently executed.
name|RET
name|ret
init|=
operator|(
name|RET
operator|)
operator|(
name|u
operator|.
name|getInstruction
argument_list|()
operator|.
name|getInstruction
argument_list|()
operator|)
decl_stmt|;
name|ReturnaddressType
name|t
init|=
operator|(
name|ReturnaddressType
operator|)
name|u
operator|.
name|getOutFrame
argument_list|(
name|oldchain
argument_list|)
operator|.
name|getLocals
argument_list|()
operator|.
name|get
argument_list|(
name|ret
operator|.
name|getIndex
argument_list|()
argument_list|)
decl_stmt|;
name|InstructionContext
name|theSuccessor
init|=
name|cfg
operator|.
name|contextOf
argument_list|(
name|t
operator|.
name|getTarget
argument_list|()
argument_list|)
decl_stmt|;
comment|// Sanity check
name|InstructionContext
name|lastJSR
init|=
literal|null
decl_stmt|;
name|int
name|skip_jsr
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|ss
init|=
name|oldchain
operator|.
name|size
argument_list|()
operator|-
literal|1
init|;
name|ss
operator|>=
literal|0
condition|;
name|ss
operator|--
control|)
block|{
if|if
condition|(
name|skip_jsr
operator|<
literal|0
condition|)
block|{
throw|throw
operator|new
name|AssertionViolatedException
argument_list|(
literal|"More RET than JSR in execution chain?!"
argument_list|)
throw|;
block|}
comment|//System.err.println("+"+oldchain.get(ss));
if|if
condition|(
operator|(
operator|(
name|InstructionContext
operator|)
name|oldchain
operator|.
name|get
argument_list|(
name|ss
argument_list|)
operator|)
operator|.
name|getInstruction
argument_list|()
operator|.
name|getInstruction
argument_list|()
operator|instanceof
name|JsrInstruction
condition|)
block|{
if|if
condition|(
name|skip_jsr
operator|==
literal|0
condition|)
block|{
name|lastJSR
operator|=
operator|(
name|InstructionContext
operator|)
name|oldchain
operator|.
name|get
argument_list|(
name|ss
argument_list|)
expr_stmt|;
break|break;
block|}
else|else
block|{
name|skip_jsr
operator|--
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|(
operator|(
name|InstructionContext
operator|)
name|oldchain
operator|.
name|get
argument_list|(
name|ss
argument_list|)
operator|)
operator|.
name|getInstruction
argument_list|()
operator|.
name|getInstruction
argument_list|()
operator|instanceof
name|RET
condition|)
block|{
name|skip_jsr
operator|++
expr_stmt|;
block|}
block|}
if|if
condition|(
name|lastJSR
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|AssertionViolatedException
argument_list|(
literal|"RET without a JSR before in ExecutionChain?! EC: '"
operator|+
name|oldchain
operator|+
literal|"'."
argument_list|)
throw|;
block|}
name|JsrInstruction
name|jsr
init|=
operator|(
name|JsrInstruction
operator|)
operator|(
name|lastJSR
operator|.
name|getInstruction
argument_list|()
operator|.
name|getInstruction
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|theSuccessor
operator|!=
operator|(
name|cfg
operator|.
name|contextOf
argument_list|(
name|jsr
operator|.
name|physicalSuccessor
argument_list|()
argument_list|)
operator|)
condition|)
block|{
throw|throw
operator|new
name|AssertionViolatedException
argument_list|(
literal|"RET '"
operator|+
name|u
operator|.
name|getInstruction
argument_list|()
operator|+
literal|"' info inconsistent: jump back to '"
operator|+
name|theSuccessor
operator|+
literal|"' or '"
operator|+
name|cfg
operator|.
name|contextOf
argument_list|(
name|jsr
operator|.
name|physicalSuccessor
argument_list|()
argument_list|)
operator|+
literal|"'?"
argument_list|)
throw|;
block|}
if|if
condition|(
name|theSuccessor
operator|.
name|execute
argument_list|(
name|u
operator|.
name|getOutFrame
argument_list|(
name|oldchain
argument_list|)
argument_list|,
name|newchain
argument_list|,
name|icv
argument_list|,
name|ev
argument_list|)
condition|)
block|{
name|icq
operator|.
name|add
argument_list|(
name|theSuccessor
argument_list|,
operator|(
name|ArrayList
operator|)
name|newchain
operator|.
name|clone
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// "not a ret"
comment|// Normal successors. Add them to the queue of successors.
name|InstructionContext
index|[]
name|succs
init|=
name|u
operator|.
name|getSuccessors
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|s
init|=
literal|0
init|;
name|s
operator|<
name|succs
operator|.
name|length
condition|;
name|s
operator|++
control|)
block|{
name|InstructionContext
name|v
init|=
name|succs
index|[
name|s
index|]
decl_stmt|;
if|if
condition|(
name|v
operator|.
name|execute
argument_list|(
name|u
operator|.
name|getOutFrame
argument_list|(
name|oldchain
argument_list|)
argument_list|,
name|newchain
argument_list|,
name|icv
argument_list|,
name|ev
argument_list|)
condition|)
block|{
name|icq
operator|.
name|add
argument_list|(
name|v
argument_list|,
operator|(
name|ArrayList
operator|)
name|newchain
operator|.
name|clone
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// end "not a ret"
comment|// Exception Handlers. Add them to the queue of successors.
comment|// [subroutines are never protected; mandated by JustIce]
name|ExceptionHandler
index|[]
name|exc_hds
init|=
name|u
operator|.
name|getExceptionHandlers
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|s
init|=
literal|0
init|;
name|s
operator|<
name|exc_hds
operator|.
name|length
condition|;
name|s
operator|++
control|)
block|{
name|InstructionContext
name|v
init|=
name|cfg
operator|.
name|contextOf
argument_list|(
name|exc_hds
index|[
name|s
index|]
operator|.
name|getHandlerStart
argument_list|()
argument_list|)
decl_stmt|;
comment|// TODO: the "oldchain" and "newchain" is used to determine the subroutine
comment|// we're in (by searching for the last JSR) by the InstructionContext
comment|// implementation. Therefore, we should not use this chain mechanism
comment|// when dealing with exception handlers.
comment|// Example: a JSR with an exception handler as its successor does not
comment|// mean we're in a subroutine if we go to the exception handler.
comment|// We should address this problem later; by now we simply "cut" the chain
comment|// by using an empty chain for the exception handlers.
comment|//if (v.execute(new Frame(u.getOutFrame(oldchain).getLocals(), new OperandStack (u.getOutFrame().getStack().maxStack(), (exc_hds[s].getExceptionType()==null? Type.THROWABLE : exc_hds[s].getExceptionType())) ), newchain), icv, ev){
comment|//icq.add(v, (ArrayList) newchain.clone());
if|if
condition|(
name|v
operator|.
name|execute
argument_list|(
operator|new
name|Frame
argument_list|(
name|u
operator|.
name|getOutFrame
argument_list|(
name|oldchain
argument_list|)
operator|.
name|getLocals
argument_list|()
argument_list|,
operator|new
name|OperandStack
argument_list|(
name|u
operator|.
name|getOutFrame
argument_list|(
name|oldchain
argument_list|)
operator|.
name|getStack
argument_list|()
operator|.
name|maxStack
argument_list|()
argument_list|,
operator|(
name|exc_hds
index|[
name|s
index|]
operator|.
name|getExceptionType
argument_list|()
operator|==
literal|null
condition|?
name|Type
operator|.
name|THROWABLE
else|:
name|exc_hds
index|[
name|s
index|]
operator|.
name|getExceptionType
argument_list|()
operator|)
argument_list|)
argument_list|)
argument_list|,
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|icv
argument_list|,
name|ev
argument_list|)
condition|)
block|{
name|icq
operator|.
name|add
argument_list|(
name|v
argument_list|,
operator|new
name|ArrayList
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// while (!icq.isEmpty()) END
name|InstructionHandle
name|ih
init|=
name|start
operator|.
name|getInstruction
argument_list|()
decl_stmt|;
do|do
block|{
if|if
condition|(
operator|(
name|ih
operator|.
name|getInstruction
argument_list|()
operator|instanceof
name|ReturnInstruction
operator|)
operator|&&
operator|(
operator|!
operator|(
name|cfg
operator|.
name|isDead
argument_list|(
name|ih
argument_list|)
operator|)
operator|)
condition|)
block|{
name|InstructionContext
name|ic
init|=
name|cfg
operator|.
name|contextOf
argument_list|(
name|ih
argument_list|)
decl_stmt|;
name|Frame
name|f
init|=
name|ic
operator|.
name|getOutFrame
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|)
decl_stmt|;
comment|// TODO: This is buggy, we check only the top-level return instructions this way. Maybe some maniac returns from a method when in a subroutine?
name|LocalVariables
name|lvs
init|=
name|f
operator|.
name|getLocals
argument_list|()
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
name|lvs
operator|.
name|maxLocals
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|lvs
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|instanceof
name|UninitializedObjectType
condition|)
block|{
name|this
operator|.
name|addMessage
argument_list|(
literal|"Warning: ReturnInstruction '"
operator|+
name|ic
operator|+
literal|"' may leave method with an uninitialized object in the local variables array '"
operator|+
name|lvs
operator|+
literal|"'."
argument_list|)
expr_stmt|;
block|}
block|}
name|OperandStack
name|os
init|=
name|f
operator|.
name|getStack
argument_list|()
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
name|os
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|os
operator|.
name|peek
argument_list|(
name|i
argument_list|)
operator|instanceof
name|UninitializedObjectType
condition|)
block|{
name|this
operator|.
name|addMessage
argument_list|(
literal|"Warning: ReturnInstruction '"
operator|+
name|ic
operator|+
literal|"' may leave method with an uninitialized object on the operand stack '"
operator|+
name|os
operator|+
literal|"'."
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
do|while
condition|(
operator|(
name|ih
operator|=
name|ih
operator|.
name|getNext
argument_list|()
operator|)
operator|!=
literal|null
condition|)
do|;
block|}
comment|/** 	 * Pass 3b implements the data flow analysis as described in the Java Virtual 	 * Machine Specification, Second Edition.  	 * Later versions will use LocalVariablesInfo objects to verify if the  	 * verifier-inferred types and the class file's debug information (LocalVariables  	 * attributes) match [TODO].  	 *  	 * @see org.apache.bcel.verifier.statics.LocalVariablesInfo  	 * @see org.apache.bcel.verifier.statics.Pass2Verifier#getLocalVariablesInfo(int)  	 */
specifier|public
name|VerificationResult
name|do_verify
parameter_list|()
block|{
if|if
condition|(
operator|!
name|myOwner
operator|.
name|doPass3a
argument_list|(
name|method_no
argument_list|)
operator|.
name|equals
argument_list|(
name|VerificationResult
operator|.
name|VR_OK
argument_list|)
condition|)
block|{
return|return
name|VerificationResult
operator|.
name|VR_NOTYET
return|;
block|}
comment|// Pass 3a ran before, so it's safe to assume the JavaClass object is
comment|// in the BCEL repository.
name|JavaClass
name|jc
decl_stmt|;
try|try
block|{
name|jc
operator|=
name|Repository
operator|.
name|lookupClass
argument_list|(
name|myOwner
operator|.
name|getClassName
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|e
parameter_list|)
block|{
comment|// FIXME: maybe not the best way to handle this
throw|throw
operator|new
name|AssertionViolatedException
argument_list|(
literal|"Missing class: "
operator|+
name|e
operator|.
name|toString
argument_list|()
argument_list|)
throw|;
block|}
name|ConstantPoolGen
name|constantPoolGen
init|=
operator|new
name|ConstantPoolGen
argument_list|(
name|jc
operator|.
name|getConstantPool
argument_list|()
argument_list|)
decl_stmt|;
comment|// Init Visitors
name|InstConstraintVisitor
name|icv
init|=
operator|new
name|InstConstraintVisitor
argument_list|()
decl_stmt|;
name|icv
operator|.
name|setConstantPoolGen
argument_list|(
name|constantPoolGen
argument_list|)
expr_stmt|;
name|ExecutionVisitor
name|ev
init|=
operator|new
name|ExecutionVisitor
argument_list|()
decl_stmt|;
name|ev
operator|.
name|setConstantPoolGen
argument_list|(
name|constantPoolGen
argument_list|)
expr_stmt|;
name|Method
index|[]
name|methods
init|=
name|jc
operator|.
name|getMethods
argument_list|()
decl_stmt|;
comment|// Method no "method_no" exists, we ran Pass3a before on it!
try|try
block|{
name|MethodGen
name|mg
init|=
operator|new
name|MethodGen
argument_list|(
name|methods
index|[
name|method_no
index|]
argument_list|,
name|myOwner
operator|.
name|getClassName
argument_list|()
argument_list|,
name|constantPoolGen
argument_list|)
decl_stmt|;
name|icv
operator|.
name|setMethodGen
argument_list|(
name|mg
argument_list|)
expr_stmt|;
comment|////////////// DFA BEGINS HERE ////////////////
if|if
condition|(
operator|!
operator|(
name|mg
operator|.
name|isAbstract
argument_list|()
operator|||
name|mg
operator|.
name|isNative
argument_list|()
operator|)
condition|)
block|{
comment|// IF mg HAS CODE (See pass 2)
name|ControlFlowGraph
name|cfg
init|=
operator|new
name|ControlFlowGraph
argument_list|(
name|mg
argument_list|)
decl_stmt|;
comment|// Build the initial frame situation for this method.
name|Frame
name|f
init|=
operator|new
name|Frame
argument_list|(
name|mg
operator|.
name|getMaxLocals
argument_list|()
argument_list|,
name|mg
operator|.
name|getMaxStack
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|mg
operator|.
name|isStatic
argument_list|()
condition|)
block|{
if|if
condition|(
name|mg
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|Constants
operator|.
name|CONSTRUCTOR_NAME
argument_list|)
condition|)
block|{
name|Frame
operator|.
name|_this
operator|=
operator|new
name|UninitializedObjectType
argument_list|(
operator|new
name|ObjectType
argument_list|(
name|jc
operator|.
name|getClassName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|f
operator|.
name|getLocals
argument_list|()
operator|.
name|set
argument_list|(
literal|0
argument_list|,
name|Frame
operator|.
name|_this
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Frame
operator|.
name|_this
operator|=
literal|null
expr_stmt|;
name|f
operator|.
name|getLocals
argument_list|()
operator|.
name|set
argument_list|(
literal|0
argument_list|,
operator|new
name|ObjectType
argument_list|(
name|jc
operator|.
name|getClassName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|Type
index|[]
name|argtypes
init|=
name|mg
operator|.
name|getArgumentTypes
argument_list|()
decl_stmt|;
name|int
name|twoslotoffset
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|argtypes
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
if|if
condition|(
name|argtypes
index|[
name|j
index|]
operator|==
name|Type
operator|.
name|SHORT
operator|||
name|argtypes
index|[
name|j
index|]
operator|==
name|Type
operator|.
name|BYTE
operator|||
name|argtypes
index|[
name|j
index|]
operator|==
name|Type
operator|.
name|CHAR
operator|||
name|argtypes
index|[
name|j
index|]
operator|==
name|Type
operator|.
name|BOOLEAN
condition|)
block|{
name|argtypes
index|[
name|j
index|]
operator|=
name|Type
operator|.
name|INT
expr_stmt|;
block|}
name|f
operator|.
name|getLocals
argument_list|()
operator|.
name|set
argument_list|(
name|twoslotoffset
operator|+
name|j
operator|+
operator|(
name|mg
operator|.
name|isStatic
argument_list|()
condition|?
literal|0
else|:
literal|1
operator|)
argument_list|,
name|argtypes
index|[
name|j
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|argtypes
index|[
name|j
index|]
operator|.
name|getSize
argument_list|()
operator|==
literal|2
condition|)
block|{
name|twoslotoffset
operator|++
expr_stmt|;
name|f
operator|.
name|getLocals
argument_list|()
operator|.
name|set
argument_list|(
name|twoslotoffset
operator|+
name|j
operator|+
operator|(
name|mg
operator|.
name|isStatic
argument_list|()
condition|?
literal|0
else|:
literal|1
operator|)
argument_list|,
name|Type
operator|.
name|UNKNOWN
argument_list|)
expr_stmt|;
block|}
block|}
name|circulationPump
argument_list|(
name|cfg
argument_list|,
name|cfg
operator|.
name|contextOf
argument_list|(
name|mg
operator|.
name|getInstructionList
argument_list|()
operator|.
name|getStart
argument_list|()
argument_list|)
argument_list|,
name|f
argument_list|,
name|icv
argument_list|,
name|ev
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|VerifierConstraintViolatedException
name|ce
parameter_list|)
block|{
name|ce
operator|.
name|extendMessage
argument_list|(
literal|"Constraint violated in method '"
operator|+
name|methods
index|[
name|method_no
index|]
operator|+
literal|"':\n"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
return|return
operator|new
name|VerificationResult
argument_list|(
name|VerificationResult
operator|.
name|VERIFIED_REJECTED
argument_list|,
name|ce
operator|.
name|getMessage
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|re
parameter_list|)
block|{
comment|// These are internal errors
name|StringWriter
name|sw
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|PrintWriter
name|pw
init|=
operator|new
name|PrintWriter
argument_list|(
name|sw
argument_list|)
decl_stmt|;
name|re
operator|.
name|printStackTrace
argument_list|(
name|pw
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|AssertionViolatedException
argument_list|(
literal|"Some RuntimeException occured while verify()ing class '"
operator|+
name|jc
operator|.
name|getClassName
argument_list|()
operator|+
literal|"', method '"
operator|+
name|methods
index|[
name|method_no
index|]
operator|+
literal|"'. Original RuntimeException's stack trace:\n---\n"
operator|+
name|sw
operator|+
literal|"---\n"
argument_list|)
throw|;
block|}
return|return
name|VerificationResult
operator|.
name|VR_OK
return|;
block|}
comment|/** Returns the method number as supplied when instantiating. */
specifier|public
name|int
name|getMethodNo
parameter_list|()
block|{
return|return
name|method_no
return|;
block|}
block|}
end_class

end_unit
