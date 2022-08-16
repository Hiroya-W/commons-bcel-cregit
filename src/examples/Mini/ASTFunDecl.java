begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  *  */
end_comment

begin_comment
comment|/* Generated By:JJTree: Do not edit this line. ASTFunDecl.java */
end_comment

begin_comment
comment|/* JJT: 0.3pre1 */
end_comment

begin_package
package|package
name|Mini
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
name|util
operator|.
name|Iterator
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
name|ALOAD
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
name|ASTORE
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
name|ArrayType
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
name|BranchHandle
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
name|ClassGen
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
name|GETSTATIC
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
name|GOTO
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
name|INVOKEVIRTUAL
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
name|InstructionConstants
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
name|InstructionList
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
name|InstructionTargeter
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
name|LocalVariableGen
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
name|TargetLostException
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
name|util
operator|.
name|InstructionFinder
import|;
end_import

begin_comment
comment|/**  *  */
end_comment

begin_class
specifier|public
class|class
name|ASTFunDecl
extends|extends
name|SimpleNode
implements|implements
name|MiniParserTreeConstants
implements|,
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Constants
block|{
specifier|private
specifier|static
specifier|final
name|InstructionFinder
operator|.
name|CodeConstraint
name|my_constraint
init|=
name|match
lambda|->
block|{
specifier|final
name|BranchInstruction
name|if_icmp
init|=
operator|(
name|BranchInstruction
operator|)
name|match
index|[
literal|0
index|]
operator|.
name|getInstruction
argument_list|()
decl_stmt|;
specifier|final
name|GOTO
name|goto_
init|=
operator|(
name|GOTO
operator|)
name|match
index|[
literal|2
index|]
operator|.
name|getInstruction
argument_list|()
decl_stmt|;
return|return
name|if_icmp
operator|.
name|getTarget
argument_list|()
operator|==
name|match
index|[
literal|3
index|]
operator|&&
name|goto_
operator|.
name|getTarget
argument_list|()
operator|==
name|match
index|[
literal|4
index|]
return|;
block|}
decl_stmt|;
comment|/*      * Used to simpulate stack with local vars and compute maximum stack size.      */
specifier|static
name|int
name|size
decl_stmt|,
name|max_size
decl_stmt|;
specifier|private
specifier|static
name|String
name|getVarDecls
parameter_list|()
block|{
specifier|final
name|StringBuilder
name|buf
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"    int "
argument_list|)
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
name|max_size
condition|;
name|i
operator|++
control|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|"_s"
operator|+
name|i
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|max_size
operator|-
literal|1
condition|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
block|}
name|buf
operator|.
name|append
argument_list|(
literal|";\n"
argument_list|)
expr_stmt|;
return|return
name|buf
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|Node
name|jjtCreate
parameter_list|(
specifier|final
name|MiniParser
name|p
parameter_list|,
specifier|final
name|int
name|id
parameter_list|)
block|{
return|return
operator|new
name|ASTFunDecl
argument_list|(
name|p
argument_list|,
name|id
argument_list|)
return|;
block|}
comment|/**      * Replaces instruction sequences (typically generated by ASTIfExpr) of the form      *      * IF_ICMP__, ICONST_1, GOTO, ICONST_0, IFEQ, Instruction      *      * where the IF_ICMP__ branches to the ICONST_0 (else part) and the GOTO branches to the IFEQ with the simpler      * expression      *      * IF_ICMP__, Instruction      *      * where the IF_ICMP__ now branches to the target of the previous IFEQ instruction.      */
specifier|private
specifier|static
name|void
name|optimizeIFs
parameter_list|(
specifier|final
name|InstructionList
name|il
parameter_list|)
block|{
specifier|final
name|InstructionFinder
name|f
init|=
operator|new
name|InstructionFinder
argument_list|(
name|il
argument_list|)
decl_stmt|;
specifier|final
name|String
name|pat
init|=
literal|"IF_ICMP ICONST_1 GOTO ICONST_0 IFEQ Instruction"
decl_stmt|;
for|for
control|(
specifier|final
name|Iterator
argument_list|<
name|InstructionHandle
index|[]
argument_list|>
name|it
init|=
name|f
operator|.
name|search
argument_list|(
name|pat
argument_list|,
name|my_constraint
argument_list|)
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
specifier|final
name|InstructionHandle
index|[]
name|match
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
comment|// Everything ok, update code
specifier|final
name|BranchInstruction
name|ifeq
init|=
operator|(
name|BranchInstruction
operator|)
name|match
index|[
literal|4
index|]
operator|.
name|getInstruction
argument_list|()
decl_stmt|;
specifier|final
name|BranchHandle
name|if_icmp
init|=
operator|(
name|BranchHandle
operator|)
name|match
index|[
literal|0
index|]
decl_stmt|;
name|if_icmp
operator|.
name|setTarget
argument_list|(
name|ifeq
operator|.
name|getTarget
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|il
operator|.
name|delete
argument_list|(
name|match
index|[
literal|1
index|]
argument_list|,
name|match
index|[
literal|4
index|]
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
specifier|final
name|TargetLostException
name|e
parameter_list|)
block|{
specifier|final
name|InstructionHandle
index|[]
name|targets
init|=
name|e
operator|.
name|getTargets
argument_list|()
decl_stmt|;
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|targets
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|InstructionHandle
name|target
range|:
name|targets
control|)
block|{
specifier|final
name|InstructionTargeter
index|[]
name|targeters
init|=
name|target
operator|.
name|getTargeters
argument_list|()
decl_stmt|;
for|for
control|(
specifier|final
name|InstructionTargeter
name|targeter
range|:
name|targeters
control|)
block|{
if|if
condition|(
name|target
operator|!=
name|match
index|[
literal|4
index|]
operator|||
name|targeter
operator|!=
name|match
index|[
literal|2
index|]
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Unexpected: "
operator|+
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
block|}
specifier|static
name|String
name|pop
parameter_list|()
block|{
return|return
literal|"_s"
operator|+
operator|--
name|size
return|;
block|}
comment|/**      * Used by byte_code()      */
specifier|static
name|void
name|pop
parameter_list|(
specifier|final
name|int
name|s
parameter_list|)
block|{
name|size
operator|-=
name|s
expr_stmt|;
block|}
specifier|static
name|void
name|push
parameter_list|()
block|{
name|push
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
specifier|static
name|void
name|push
parameter_list|(
specifier|final
name|int
name|s
parameter_list|)
block|{
name|size
operator|+=
name|s
expr_stmt|;
if|if
condition|(
name|size
operator|>
name|max_size
condition|)
block|{
name|max_size
operator|=
name|size
expr_stmt|;
block|}
block|}
comment|/**      * Used byte code()      */
specifier|static
name|void
name|push
parameter_list|(
specifier|final
name|StringBuffer
name|buf
parameter_list|,
specifier|final
name|String
name|str
parameter_list|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|"    _s"
operator|+
name|size
operator|+
literal|" = "
operator|+
name|str
operator|+
literal|";\n"
argument_list|)
expr_stmt|;
name|push
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
specifier|static
name|void
name|reset
parameter_list|()
block|{
name|size
operator|=
name|max_size
operator|=
literal|0
expr_stmt|;
block|}
specifier|private
name|ASTIdent
name|name
decl_stmt|;
specifier|private
name|ASTIdent
index|[]
name|argv
decl_stmt|;
specifier|private
name|ASTExpr
name|body
decl_stmt|;
specifier|private
name|int
name|type
init|=
name|T_UNKNOWN
decl_stmt|;
specifier|private
name|int
name|line
decl_stmt|,
name|column
decl_stmt|;
specifier|private
name|boolean
name|is_simple
decl_stmt|;
comment|// true, if simple expression like `12 + f(a)'
specifier|private
name|boolean
name|is_recursive
decl_stmt|;
comment|// Not used yet, TODO
comment|// private int max_depth; // max. expression tree depth
specifier|private
name|Environment
name|env
decl_stmt|;
name|ASTFunDecl
parameter_list|(
specifier|final
name|ASTIdent
name|name
parameter_list|,
specifier|final
name|ASTIdent
index|[]
name|argv
parameter_list|,
specifier|final
name|ASTExpr
name|body
parameter_list|,
specifier|final
name|int
name|type
parameter_list|)
block|{
name|this
argument_list|(
name|JJTFUNDECL
argument_list|)
expr_stmt|;
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|this
operator|.
name|argv
operator|=
name|argv
expr_stmt|;
name|this
operator|.
name|body
operator|=
name|body
expr_stmt|;
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
block|}
comment|// Generated methods
name|ASTFunDecl
parameter_list|(
specifier|final
name|int
name|id
parameter_list|)
block|{
name|super
argument_list|(
name|id
argument_list|)
expr_stmt|;
block|}
name|ASTFunDecl
parameter_list|(
specifier|final
name|MiniParser
name|p
parameter_list|,
specifier|final
name|int
name|id
parameter_list|)
block|{
name|super
argument_list|(
name|p
argument_list|,
name|id
argument_list|)
expr_stmt|;
block|}
comment|/**      * Fifth pass, produce Java byte code.      */
specifier|public
name|void
name|byte_code
parameter_list|(
specifier|final
name|ClassGen
name|classGen
parameter_list|,
specifier|final
name|ConstantPoolGen
name|cp
parameter_list|)
block|{
name|MethodGen
name|method
init|=
literal|null
decl_stmt|;
name|boolean
name|main
init|=
literal|false
decl_stmt|,
name|ignore
init|=
literal|false
decl_stmt|;
specifier|final
name|String
name|className
init|=
name|classGen
operator|.
name|getClassName
argument_list|()
decl_stmt|;
specifier|final
name|String
name|fname
init|=
name|name
operator|.
name|getName
argument_list|()
decl_stmt|;
specifier|final
name|InstructionList
name|il
init|=
operator|new
name|InstructionList
argument_list|()
decl_stmt|;
name|Type
index|[]
name|args
init|=
block|{
operator|new
name|ArrayType
argument_list|(
name|Type
operator|.
name|STRING
argument_list|,
literal|1
argument_list|)
block|}
decl_stmt|;
comment|// default for `main'
name|String
index|[]
name|arg_names
init|=
block|{
literal|"$argv"
block|}
decl_stmt|;
if|if
condition|(
name|fname
operator|.
name|equals
argument_list|(
literal|"main"
argument_list|)
condition|)
block|{
name|method
operator|=
operator|new
name|MethodGen
argument_list|(
name|ACC_STATIC
operator||
name|ACC_PUBLIC
argument_list|,
name|Type
operator|.
name|VOID
argument_list|,
name|args
argument_list|,
name|arg_names
argument_list|,
literal|"main"
argument_list|,
name|className
argument_list|,
name|il
argument_list|,
name|cp
argument_list|)
expr_stmt|;
name|main
operator|=
literal|true
expr_stmt|;
block|}
if|else if
condition|(
name|fname
operator|.
name|equals
argument_list|(
literal|"READ"
argument_list|)
operator|||
name|fname
operator|.
name|equals
argument_list|(
literal|"WRITE"
argument_list|)
condition|)
block|{
comment|// Do nothing
name|ignore
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
specifier|final
name|int
name|size
init|=
name|argv
operator|.
name|length
decl_stmt|;
name|arg_names
operator|=
operator|new
name|String
index|[
name|size
index|]
expr_stmt|;
name|args
operator|=
operator|new
name|Type
index|[
name|size
index|]
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|size
condition|;
name|i
operator|++
control|)
block|{
name|args
index|[
name|i
index|]
operator|=
name|Type
operator|.
name|INT
expr_stmt|;
name|arg_names
index|[
name|i
index|]
operator|=
name|argv
index|[
name|i
index|]
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
name|method
operator|=
operator|new
name|MethodGen
argument_list|(
name|ACC_STATIC
operator||
name|ACC_PRIVATE
operator||
name|ACC_FINAL
argument_list|,
name|Type
operator|.
name|INT
argument_list|,
name|args
argument_list|,
name|arg_names
argument_list|,
name|fname
argument_list|,
name|className
argument_list|,
name|il
argument_list|,
name|cp
argument_list|)
expr_stmt|;
specifier|final
name|LocalVariableGen
index|[]
name|lv
init|=
name|method
operator|.
name|getLocalVariables
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
name|size
condition|;
name|i
operator|++
control|)
block|{
specifier|final
name|Variable
name|entry
init|=
operator|(
name|Variable
operator|)
name|env
operator|.
name|get
argument_list|(
name|arg_names
index|[
name|i
index|]
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setLocalVariable
argument_list|(
name|lv
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
name|method
operator|.
name|addException
argument_list|(
literal|"java.io.IOException"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|ignore
condition|)
block|{
name|body
operator|.
name|byte_code
argument_list|(
name|il
argument_list|,
name|method
argument_list|,
name|cp
argument_list|)
expr_stmt|;
if|if
condition|(
name|main
condition|)
block|{
specifier|final
name|ObjectType
name|eType
init|=
operator|new
name|ObjectType
argument_list|(
literal|"java.lang.Exception"
argument_list|)
decl_stmt|;
specifier|final
name|InstructionHandle
name|start
init|=
name|il
operator|.
name|getStart
argument_list|()
decl_stmt|;
name|InstructionHandle
name|end
decl_stmt|,
name|handler
decl_stmt|,
name|end_handler
decl_stmt|;
specifier|final
name|LocalVariableGen
name|exc
init|=
name|method
operator|.
name|addLocalVariable
argument_list|(
literal|"$e"
argument_list|,
name|eType
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
decl_stmt|;
specifier|final
name|int
name|slot
init|=
name|exc
operator|.
name|getIndex
argument_list|()
decl_stmt|;
name|il
operator|.
name|append
argument_list|(
name|InstructionConstants
operator|.
name|POP
argument_list|)
expr_stmt|;
name|pop
argument_list|()
expr_stmt|;
comment|// Remove last element on stack
name|end
operator|=
name|il
operator|.
name|append
argument_list|(
name|InstructionConstants
operator|.
name|RETURN
argument_list|)
expr_stmt|;
comment|// Use instruction constants, if possible
comment|// catch
name|handler
operator|=
name|il
operator|.
name|append
argument_list|(
operator|new
name|ASTORE
argument_list|(
name|slot
argument_list|)
argument_list|)
expr_stmt|;
comment|// save exception object
name|il
operator|.
name|append
argument_list|(
operator|new
name|GETSTATIC
argument_list|(
name|cp
operator|.
name|addFieldref
argument_list|(
literal|"java.lang.System"
argument_list|,
literal|"err"
argument_list|,
literal|"Ljava/io/PrintStream;"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|ALOAD
argument_list|(
name|slot
argument_list|)
argument_list|)
expr_stmt|;
name|push
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|INVOKEVIRTUAL
argument_list|(
name|cp
operator|.
name|addMethodref
argument_list|(
literal|"java.io.PrintStream"
argument_list|,
literal|"println"
argument_list|,
literal|"(Ljava/lang/Object;)V"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|pop
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|end_handler
operator|=
name|il
operator|.
name|append
argument_list|(
name|InstructionConstants
operator|.
name|RETURN
argument_list|)
expr_stmt|;
name|method
operator|.
name|addExceptionHandler
argument_list|(
name|start
argument_list|,
name|end
argument_list|,
name|handler
argument_list|,
name|eType
argument_list|)
expr_stmt|;
name|exc
operator|.
name|setStart
argument_list|(
name|handler
argument_list|)
expr_stmt|;
name|exc
operator|.
name|setEnd
argument_list|(
name|end_handler
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|il
operator|.
name|append
argument_list|(
name|InstructionConstants
operator|.
name|IRETURN
argument_list|)
expr_stmt|;
comment|// Reuse object to save memory
block|}
name|method
operator|.
name|removeNOPs
argument_list|()
expr_stmt|;
comment|// First optimization pass, provided by MethodGen
name|optimizeIFs
argument_list|(
name|il
argument_list|)
expr_stmt|;
comment|// Second optimization pass, application-specific
name|method
operator|.
name|setMaxStack
argument_list|(
name|max_size
argument_list|)
expr_stmt|;
name|classGen
operator|.
name|addMethod
argument_list|(
name|method
operator|.
name|getMethod
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|il
operator|.
name|dispose
argument_list|()
expr_stmt|;
comment|// Dispose instruction handles for better memory utilization
name|reset
argument_list|()
expr_stmt|;
block|}
comment|/**      * Overrides SimpleNode.closeNode() Cast children to appropiate type.      */
annotation|@
name|Override
specifier|public
name|void
name|closeNode
parameter_list|()
block|{
name|name
operator|=
operator|(
name|ASTIdent
operator|)
name|children
index|[
literal|0
index|]
expr_stmt|;
name|body
operator|=
operator|(
name|ASTExpr
operator|)
name|children
index|[
name|children
operator|.
name|length
operator|-
literal|1
index|]
expr_stmt|;
name|argv
operator|=
operator|new
name|ASTIdent
index|[
name|children
operator|.
name|length
operator|-
literal|2
index|]
expr_stmt|;
comment|// May be 0-size array
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|children
operator|.
name|length
operator|-
literal|1
condition|;
name|i
operator|++
control|)
block|{
name|argv
index|[
name|i
operator|-
literal|1
index|]
operator|=
operator|(
name|ASTIdent
operator|)
name|children
index|[
name|i
index|]
expr_stmt|;
block|}
name|children
operator|=
literal|null
expr_stmt|;
comment|// Throw away old reference
block|}
comment|/**      * Fourth pass, produce Java code.      */
specifier|public
name|void
name|code
parameter_list|(
specifier|final
name|PrintWriter
name|out
parameter_list|)
block|{
name|String
name|expr
decl_stmt|;
name|boolean
name|main
init|=
literal|false
decl_stmt|,
name|ignore
init|=
literal|false
decl_stmt|;
specifier|final
name|String
name|fname
init|=
name|name
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|fname
operator|.
name|equals
argument_list|(
literal|"main"
argument_list|)
condition|)
block|{
name|out
operator|.
name|println
argument_list|(
literal|"  public static void main(String[] _argv) {"
argument_list|)
expr_stmt|;
name|main
operator|=
literal|true
expr_stmt|;
block|}
if|else if
condition|(
name|fname
operator|.
name|equals
argument_list|(
literal|"READ"
argument_list|)
operator|||
name|fname
operator|.
name|equals
argument_list|(
literal|"WRITE"
argument_list|)
condition|)
block|{
comment|// Do nothing
name|ignore
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
name|out
operator|.
name|print
argument_list|(
literal|"  public static final "
operator|+
literal|"int"
operator|+
comment|// type_names[type] +
literal|" "
operator|+
name|fname
operator|+
literal|"("
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|argv
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|out
operator|.
name|print
argument_list|(
literal|"int "
operator|+
name|argv
index|[
name|i
index|]
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|argv
operator|.
name|length
operator|-
literal|1
condition|)
block|{
name|out
operator|.
name|print
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
block|}
name|out
operator|.
name|println
argument_list|(
literal|")\n    throws IOException\n  {"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|ignore
condition|)
block|{
specifier|final
name|StringBuffer
name|buf
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|body
operator|.
name|code
argument_list|(
name|buf
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
name|getVarDecls
argument_list|()
argument_list|)
expr_stmt|;
name|expr
operator|=
name|buf
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|main
condition|)
block|{
name|out
operator|.
name|println
argument_list|(
literal|"    try {"
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|println
argument_list|(
name|expr
argument_list|)
expr_stmt|;
if|if
condition|(
name|main
condition|)
block|{
name|out
operator|.
name|println
argument_list|(
literal|"    } catch(Exception e) { System.err.println(e); }\n  }\n"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|out
operator|.
name|println
argument_list|(
literal|"\n    return "
operator|+
name|pop
argument_list|()
operator|+
literal|";\n  }\n"
argument_list|)
expr_stmt|;
block|}
block|}
name|reset
argument_list|()
expr_stmt|;
block|}
comment|/**      * Overrides SimpleNode.dump()      */
annotation|@
name|Override
specifier|public
name|void
name|dump
parameter_list|(
specifier|final
name|String
name|prefix
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|toString
argument_list|(
name|prefix
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|ASTIdent
name|element
range|:
name|argv
control|)
block|{
name|element
operator|.
name|dump
argument_list|(
name|prefix
operator|+
literal|" "
argument_list|)
expr_stmt|;
block|}
name|body
operator|.
name|dump
argument_list|(
name|prefix
operator|+
literal|" "
argument_list|)
expr_stmt|;
block|}
comment|/**      * Second pass      *      * @return type of expression      */
specifier|public
name|int
name|eval
parameter_list|(
specifier|final
name|int
name|pass
parameter_list|)
block|{
specifier|final
name|int
name|expected
init|=
name|name
operator|.
name|getType
argument_list|()
decl_stmt|;
comment|// Maybe other function has already called us
name|type
operator|=
name|body
operator|.
name|eval
argument_list|(
name|expected
argument_list|)
expr_stmt|;
comment|// And updated the env
if|if
condition|(
name|expected
operator|!=
name|T_UNKNOWN
operator|&&
name|type
operator|!=
name|expected
condition|)
block|{
name|MiniC
operator|.
name|addError
argument_list|(
name|line
argument_list|,
name|column
argument_list|,
literal|"Function f ist not of type "
operator|+
name|TYPE_NAMES
index|[
name|expected
index|]
operator|+
literal|" as previously assumed, but "
operator|+
name|TYPE_NAMES
index|[
name|type
index|]
argument_list|)
expr_stmt|;
block|}
name|name
operator|.
name|setType
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|is_simple
operator|=
name|body
operator|.
name|isSimple
argument_list|()
expr_stmt|;
if|if
condition|(
name|pass
operator|==
literal|2
operator|&&
name|type
operator|==
name|T_UNKNOWN
condition|)
block|{
name|is_recursive
operator|=
literal|true
expr_stmt|;
block|}
return|return
name|type
return|;
block|}
specifier|public
name|ASTIdent
index|[]
name|getArgs
parameter_list|()
block|{
return|return
name|argv
return|;
block|}
specifier|public
name|int
name|getColumn
parameter_list|()
block|{
return|return
name|column
return|;
block|}
specifier|public
name|int
name|getLine
parameter_list|()
block|{
return|return
name|line
return|;
block|}
specifier|public
name|ASTIdent
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
specifier|public
name|int
name|getNoArgs
parameter_list|()
block|{
return|return
name|argv
operator|.
name|length
return|;
block|}
specifier|public
name|int
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
specifier|public
name|boolean
name|isrecursive
parameter_list|()
block|{
return|return
name|is_recursive
return|;
block|}
specifier|public
name|boolean
name|isSimple
parameter_list|()
block|{
return|return
name|is_simple
return|;
block|}
specifier|public
name|void
name|setColumn
parameter_list|(
specifier|final
name|int
name|column
parameter_list|)
block|{
name|this
operator|.
name|column
operator|=
name|column
expr_stmt|;
block|}
specifier|public
name|void
name|setLine
parameter_list|(
specifier|final
name|int
name|line
parameter_list|)
block|{
name|this
operator|.
name|line
operator|=
name|line
expr_stmt|;
block|}
specifier|public
name|void
name|setPosition
parameter_list|(
specifier|final
name|int
name|line
parameter_list|,
specifier|final
name|int
name|column
parameter_list|)
block|{
name|this
operator|.
name|line
operator|=
name|line
expr_stmt|;
name|this
operator|.
name|column
operator|=
name|column
expr_stmt|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
specifier|final
name|int
name|type
parameter_list|)
block|{
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
block|}
comment|/**      * Overrides SimpleNode.toString()      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
specifier|final
name|StringBuilder
name|buf
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|buf
operator|.
name|append
argument_list|(
name|jjtNodeName
index|[
name|id
index|]
operator|+
literal|" "
operator|+
name|name
operator|+
literal|"("
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|argv
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|buf
operator|.
name|append
argument_list|(
name|argv
index|[
name|i
index|]
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|argv
operator|.
name|length
operator|-
literal|1
condition|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
block|}
name|buf
operator|.
name|append
argument_list|(
literal|")"
argument_list|)
expr_stmt|;
return|return
name|buf
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * First pass of parse tree.      */
specifier|public
name|ASTFunDecl
name|traverse
parameter_list|(
specifier|final
name|Environment
name|env
parameter_list|)
block|{
name|this
operator|.
name|env
operator|=
name|env
expr_stmt|;
comment|// Put arguments into hash table aka environment
for|for
control|(
specifier|final
name|ASTIdent
name|element
range|:
name|argv
control|)
block|{
specifier|final
name|EnvEntry
name|entry
init|=
name|env
operator|.
name|get
argument_list|(
name|element
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|entry
operator|!=
literal|null
condition|)
block|{
name|MiniC
operator|.
name|addError
argument_list|(
name|element
operator|.
name|getLine
argument_list|()
argument_list|,
name|element
operator|.
name|getColumn
argument_list|()
argument_list|,
literal|"Redeclaration of "
operator|+
name|entry
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|env
operator|.
name|put
argument_list|(
operator|new
name|Variable
argument_list|(
name|element
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/*          * Update entry of this function, i.e. set argument references. The entry is already in there by garantuee, but may be          * of wrong type, i.e. the user defined a function `TRUE', e.g. and `TRUE' is of type `Variable'.          */
try|try
block|{
specifier|final
name|Function
name|fun
init|=
operator|(
name|Function
operator|)
name|env
operator|.
name|get
argument_list|(
name|name
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|fun
operator|.
name|setArgs
argument_list|(
name|argv
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
specifier|final
name|ClassCastException
name|e
parameter_list|)
block|{
block|}
comment|// Who cares?
name|body
operator|=
name|body
operator|.
name|traverse
argument_list|(
name|env
argument_list|)
expr_stmt|;
comment|// Traverse expression body
return|return
name|this
return|;
block|}
block|}
end_class

end_unit

