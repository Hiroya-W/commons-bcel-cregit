begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  *  */
end_comment

begin_package
package|package
name|Mini
package|;
end_package

begin_comment
comment|/**  * Represents a function declaration and its arguments. Type information is contained  * in the ASTIdent fields.  *  * @version $Id$  */
end_comment

begin_class
specifier|public
class|class
name|Function
implements|implements
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|bcel6
operator|.
name|Constants
implements|,
name|EnvEntry
block|{
specifier|private
name|ASTIdent
name|name
decl_stmt|;
comment|// Reference to the original declaration
specifier|private
name|ASTIdent
index|[]
name|args
decl_stmt|;
comment|// Reference to argument identifiers
comment|//  private ASTExpr    body;         // Reference to function expression body
specifier|private
name|boolean
name|reserved
decl_stmt|;
comment|// Is a key word?
specifier|private
name|int
name|line
decl_stmt|,
name|column
decl_stmt|;
comment|// Short for name.getToken()
specifier|private
name|String
name|fun_name
decl_stmt|;
comment|// Short for name.getName()
specifier|private
name|int
name|no_args
decl_stmt|;
specifier|public
name|Function
parameter_list|(
name|ASTIdent
name|name
parameter_list|,
name|ASTIdent
index|[]
name|args
parameter_list|)
block|{
name|this
argument_list|(
name|name
argument_list|,
name|args
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Function
parameter_list|(
name|ASTIdent
name|name
parameter_list|,
name|ASTIdent
index|[]
name|args
parameter_list|,
name|boolean
name|reserved
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|this
operator|.
name|args
operator|=
name|args
expr_stmt|;
name|this
operator|.
name|reserved
operator|=
name|reserved
expr_stmt|;
name|fun_name
operator|=
name|name
operator|.
name|getName
argument_list|()
expr_stmt|;
name|line
operator|=
name|name
operator|.
name|getLine
argument_list|()
expr_stmt|;
name|column
operator|=
name|name
operator|.
name|getColumn
argument_list|()
expr_stmt|;
name|setArgs
argument_list|(
name|args
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|StringBuffer
name|buf
init|=
operator|new
name|StringBuffer
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
name|no_args
condition|;
name|i
operator|++
control|)
block|{
name|buf
operator|.
name|append
argument_list|(
name|args
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
name|no_args
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
name|String
name|prefix
init|=
literal|"Function "
operator|+
name|fun_name
operator|+
literal|"("
operator|+
name|buf
operator|.
name|toString
argument_list|()
operator|+
literal|")"
decl_stmt|;
if|if
condition|(
operator|!
name|reserved
condition|)
block|{
return|return
name|prefix
operator|+
literal|" declared at line "
operator|+
name|line
operator|+
literal|", column "
operator|+
name|column
return|;
block|}
else|else
block|{
return|return
name|prefix
operator|+
literal|"<predefined function>"
return|;
block|}
block|}
specifier|public
name|int
name|getNoArgs
parameter_list|()
block|{
return|return
name|no_args
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
name|String
name|getHashKey
parameter_list|()
block|{
return|return
name|fun_name
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
name|int
name|getColumn
parameter_list|()
block|{
return|return
name|column
return|;
block|}
specifier|public
name|ASTIdent
name|getArg
parameter_list|(
name|int
name|i
parameter_list|)
block|{
return|return
name|args
index|[
name|i
index|]
return|;
block|}
specifier|public
name|ASTIdent
index|[]
name|getArgs
parameter_list|()
block|{
return|return
name|args
return|;
block|}
specifier|public
name|void
name|setArgs
parameter_list|(
name|ASTIdent
index|[]
name|args
parameter_list|)
block|{
name|this
operator|.
name|args
operator|=
name|args
expr_stmt|;
name|no_args
operator|=
operator|(
name|args
operator|==
literal|null
operator|)
condition|?
literal|0
else|:
name|args
operator|.
name|length
expr_stmt|;
block|}
block|}
end_class

end_unit

