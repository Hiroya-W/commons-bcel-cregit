begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  *  */
end_comment

begin_comment
comment|/* Generated By:JJTree: Do not edit this line. ASTFunAppl.java */
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
name|INVOKESTATIC
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
name|Type
import|;
end_import

begin_comment
comment|/**  *  */
end_comment

begin_class
specifier|public
class|class
name|ASTFunAppl
extends|extends
name|ASTExpr
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
name|ASTIdent
name|name
decl_stmt|;
specifier|private
name|Function
name|function
decl_stmt|;
comment|// Points to Function in environment
comment|// Generated methods
name|ASTFunAppl
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
name|ASTFunAppl
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
name|ASTFunAppl
argument_list|(
name|p
argument_list|,
name|id
argument_list|)
return|;
block|}
name|ASTFunAppl
parameter_list|(
specifier|final
name|ASTIdent
name|name
parameter_list|,
specifier|final
name|Function
name|function
parameter_list|,
specifier|final
name|ASTExpr
index|[]
name|exprs
parameter_list|)
block|{
name|this
argument_list|(
name|JJTFUNAPPL
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
name|function
operator|=
name|function
expr_stmt|;
name|this
operator|.
name|exprs
operator|=
name|exprs
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|jjtNodeName
index|[
name|id
index|]
operator|+
literal|" "
operator|+
name|name
operator|.
name|getName
argument_list|()
return|;
block|}
comment|/**    * Overrides ASTExpr.closeNode()    */
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
if|if
condition|(
name|children
operator|.
name|length
operator|>
literal|1
condition|)
block|{
name|exprs
operator|=
operator|new
name|ASTExpr
index|[
name|children
operator|.
name|length
operator|-
literal|1
index|]
expr_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|children
argument_list|,
literal|1
argument_list|,
name|exprs
argument_list|,
literal|0
argument_list|,
name|children
operator|.
name|length
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
name|children
operator|=
literal|null
expr_stmt|;
comment|// Throw away old reference
block|}
comment|/**    * Overrides ASTExpr.traverse()    */
annotation|@
name|Override
specifier|public
name|ASTExpr
name|traverse
parameter_list|(
specifier|final
name|Environment
name|env
parameter_list|)
block|{
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
name|EnvEntry
name|entry
init|=
name|env
operator|.
name|get
argument_list|(
name|fname
argument_list|)
decl_stmt|;
name|this
operator|.
name|env
operator|=
name|env
expr_stmt|;
if|if
condition|(
name|entry
operator|==
literal|null
condition|)
block|{
name|MiniC
operator|.
name|addError
argument_list|(
name|name
operator|.
name|getLine
argument_list|()
argument_list|,
name|name
operator|.
name|getColumn
argument_list|()
argument_list|,
literal|"Applying unknown function "
operator|+
name|fname
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|!
operator|(
name|entry
operator|instanceof
name|Function
operator|)
condition|)
block|{
name|MiniC
operator|.
name|addError
argument_list|(
name|name
operator|.
name|getLine
argument_list|()
argument_list|,
name|name
operator|.
name|getColumn
argument_list|()
argument_list|,
literal|"Applying non-function "
operator|+
name|fname
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
specifier|final
name|int
name|len
init|=
operator|(
name|exprs
operator|!=
literal|null
operator|)
condition|?
name|exprs
operator|.
name|length
else|:
literal|0
decl_stmt|;
specifier|final
name|Function
name|fun
init|=
operator|(
name|Function
operator|)
name|entry
decl_stmt|;
if|if
condition|(
name|len
operator|!=
name|fun
operator|.
name|getNoArgs
argument_list|()
condition|)
block|{
name|MiniC
operator|.
name|addError
argument_list|(
name|name
operator|.
name|getLine
argument_list|()
argument_list|,
name|name
operator|.
name|getColumn
argument_list|()
argument_list|,
literal|"Function "
operator|+
name|fname
operator|+
literal|" expects "
operator|+
name|fun
operator|.
name|getNoArgs
argument_list|()
operator|+
literal|" arguments, you supplied "
operator|+
name|len
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Adjust references
name|function
operator|=
name|fun
expr_stmt|;
name|name
operator|=
name|fun
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
name|exprs
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|exprs
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|exprs
index|[
name|i
index|]
operator|=
name|exprs
index|[
name|i
index|]
operator|.
name|traverse
argument_list|(
name|env
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|this
return|;
block|}
comment|/**    * Second pass    * Overrides AstExpr.eval()    * @return type of expression    * @param expected type    */
annotation|@
name|Override
specifier|public
name|int
name|eval
parameter_list|(
specifier|final
name|int
name|expected
parameter_list|)
block|{
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
name|Function
name|f
init|=
name|function
decl_stmt|;
specifier|final
name|ASTIdent
name|fun
init|=
name|f
operator|.
name|getName
argument_list|()
decl_stmt|;
specifier|final
name|ASTIdent
index|[]
name|args
init|=
name|f
operator|.
name|getArgs
argument_list|()
decl_stmt|;
name|int
name|t
init|=
name|fun
operator|.
name|getType
argument_list|()
decl_stmt|;
name|is_simple
operator|=
literal|true
expr_stmt|;
comment|// Only true if all arguments are simple expressions
comment|// Check arguments
if|if
condition|(
name|exprs
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|exprs
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
comment|// length match checked in previous pass
specifier|final
name|int
name|expect
init|=
name|args
index|[
name|i
index|]
operator|.
name|getType
argument_list|()
decl_stmt|;
comment|// May be T_UNKNOWN
specifier|final
name|int
name|t_e
init|=
name|exprs
index|[
name|i
index|]
operator|.
name|eval
argument_list|(
name|expect
argument_list|)
decl_stmt|;
comment|// May be T_UNKNOWN
if|if
condition|(
operator|(
name|expect
operator|!=
name|T_UNKNOWN
operator|)
operator|&&
operator|(
name|t_e
operator|!=
name|expect
operator|)
condition|)
block|{
name|MiniC
operator|.
name|addError
argument_list|(
name|exprs
index|[
name|i
index|]
operator|.
name|getLine
argument_list|()
argument_list|,
name|exprs
index|[
name|i
index|]
operator|.
name|getColumn
argument_list|()
argument_list|,
literal|"Argument "
operator|+
operator|(
name|i
operator|+
literal|1
operator|)
operator|+
literal|" in application of "
operator|+
name|fname
operator|+
literal|" is not of type "
operator|+
name|TYPE_NAMES
index|[
name|expect
index|]
operator|+
literal|" but "
operator|+
name|TYPE_NAMES
index|[
name|t_e
index|]
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|args
index|[
name|i
index|]
operator|.
name|setType
argument_list|(
name|t_e
argument_list|)
expr_stmt|;
comment|// Update, may be identical
block|}
name|is_simple
operator|=
name|is_simple
operator|&&
name|exprs
index|[
name|i
index|]
operator|.
name|isSimple
argument_list|()
expr_stmt|;
comment|// Check condition
block|}
block|}
if|if
condition|(
name|t
operator|==
name|T_UNKNOWN
condition|)
block|{
name|fun
operator|.
name|setType
argument_list|(
name|t
operator|=
name|expected
argument_list|)
expr_stmt|;
comment|// May be still T_UNKNOWN
block|}
return|return
name|type
operator|=
name|t
return|;
block|}
comment|/**    * Fourth pass, produce Java code.    */
annotation|@
name|Override
specifier|public
name|void
name|code
parameter_list|(
specifier|final
name|StringBuffer
name|buf
parameter_list|)
block|{
specifier|final
name|String
name|fname
init|=
name|name
operator|.
name|getName
argument_list|()
decl_stmt|;
comment|//    Function   f     = function;
comment|//    ASTIdent[] args  = f.getArgs();
if|if
condition|(
name|fname
operator|.
name|equals
argument_list|(
literal|"READ"
argument_list|)
condition|)
block|{
name|ASTFunDecl
operator|.
name|push
argument_list|(
name|buf
argument_list|,
literal|"_readInt()"
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|fname
operator|.
name|equals
argument_list|(
literal|"WRITE"
argument_list|)
condition|)
block|{
name|exprs
index|[
literal|0
index|]
operator|.
name|code
argument_list|(
name|buf
argument_list|)
expr_stmt|;
name|ASTFunDecl
operator|.
name|push
argument_list|(
name|buf
argument_list|,
literal|"_writeInt("
operator|+
name|ASTFunDecl
operator|.
name|pop
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Normal function
if|if
condition|(
name|exprs
operator|!=
literal|null
condition|)
block|{
comment|// Output in reverse odrder
for|for
control|(
name|int
name|i
init|=
name|exprs
operator|.
name|length
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
name|exprs
index|[
name|i
index|]
operator|.
name|code
argument_list|(
name|buf
argument_list|)
expr_stmt|;
block|}
block|}
specifier|final
name|StringBuilder
name|call
init|=
operator|new
name|StringBuilder
argument_list|(
name|fname
operator|+
literal|"("
argument_list|)
decl_stmt|;
comment|// Function call
if|if
condition|(
name|exprs
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|exprs
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|call
operator|.
name|append
argument_list|(
name|ASTFunDecl
operator|.
name|pop
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|exprs
operator|.
name|length
operator|-
literal|1
condition|)
block|{
name|call
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|call
operator|.
name|append
argument_list|(
literal|")"
argument_list|)
expr_stmt|;
name|ASTFunDecl
operator|.
name|push
argument_list|(
name|buf
argument_list|,
name|call
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**    * Fifth pass, produce Java byte code.    */
annotation|@
name|Override
specifier|public
name|void
name|byte_code
parameter_list|(
specifier|final
name|InstructionList
name|il
parameter_list|,
specifier|final
name|MethodGen
name|method
parameter_list|,
specifier|final
name|ConstantPoolGen
name|cp
parameter_list|)
block|{
specifier|final
name|String
name|fname
init|=
name|name
operator|.
name|getName
argument_list|()
decl_stmt|;
comment|//    Function   f     = function;
comment|//ASTIdent   fun   = f.getName();
comment|//    ASTIdent[] args  = f.getArgs();
specifier|final
name|String
name|class_name
init|=
name|method
operator|.
name|getClassName
argument_list|()
decl_stmt|;
if|if
condition|(
name|fname
operator|.
name|equals
argument_list|(
literal|"READ"
argument_list|)
condition|)
block|{
name|il
operator|.
name|append
argument_list|(
operator|new
name|INVOKESTATIC
argument_list|(
name|cp
operator|.
name|addMethodref
argument_list|(
name|class_name
argument_list|,
literal|"_readInt"
argument_list|,
literal|"()I"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|fname
operator|.
name|equals
argument_list|(
literal|"WRITE"
argument_list|)
condition|)
block|{
name|exprs
index|[
literal|0
index|]
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
name|ASTFunDecl
operator|.
name|pop
argument_list|()
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|INVOKESTATIC
argument_list|(
name|cp
operator|.
name|addMethodref
argument_list|(
name|class_name
argument_list|,
literal|"_writeInt"
argument_list|,
literal|"(I)I"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Normal function
specifier|final
name|int
name|size
init|=
name|exprs
operator|.
name|length
decl_stmt|;
name|Type
index|[]
name|argv
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|exprs
operator|!=
literal|null
condition|)
block|{
name|argv
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
name|argv
index|[
name|i
index|]
operator|=
name|Type
operator|.
name|INT
expr_stmt|;
name|exprs
index|[
name|i
index|]
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
block|}
comment|//ASTFunDecl.push(size);
block|}
name|ASTFunDecl
operator|.
name|pop
argument_list|(
name|size
argument_list|)
expr_stmt|;
comment|// Function call
name|il
operator|.
name|append
argument_list|(
operator|new
name|INVOKESTATIC
argument_list|(
name|cp
operator|.
name|addMethodref
argument_list|(
name|class_name
argument_list|,
name|fname
argument_list|,
name|Type
operator|.
name|getMethodSignature
argument_list|(
name|Type
operator|.
name|INT
argument_list|,
name|argv
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|ASTFunDecl
operator|.
name|push
argument_list|()
expr_stmt|;
block|}
comment|// dump() inherited
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
name|Function
name|getFunction
parameter_list|()
block|{
return|return
name|function
return|;
block|}
block|}
end_class

end_unit

