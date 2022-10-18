begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  */
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
name|LocalVariableGen
import|;
end_import

begin_comment
comment|/**  * Represents a variable declared in a LET expression or a FUN declaration.  */
end_comment

begin_class
specifier|public
class|class
name|Variable
implements|implements
name|EnvEntry
block|{
specifier|private
specifier|final
name|ASTIdent
name|name
decl_stmt|;
comment|// Reference to the original declaration
specifier|private
specifier|final
name|boolean
name|reserved
decl_stmt|;
comment|// Is a key word?
specifier|private
specifier|final
name|int
name|line
decl_stmt|,
name|column
decl_stmt|;
comment|// Extracted from name.getToken()
specifier|private
specifier|final
name|String
name|var_name
decl_stmt|;
comment|// Short for name.getName()
specifier|private
name|LocalVariableGen
name|local_var
decl_stmt|;
comment|// local var associated with this variable
specifier|public
name|Variable
parameter_list|(
specifier|final
name|ASTIdent
name|name
parameter_list|)
block|{
name|this
argument_list|(
name|name
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Variable
parameter_list|(
specifier|final
name|ASTIdent
name|name
parameter_list|,
specifier|final
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
name|reserved
operator|=
name|reserved
expr_stmt|;
name|var_name
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
block|}
annotation|@
name|Override
specifier|public
name|int
name|getColumn
parameter_list|()
block|{
return|return
name|column
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getHashKey
parameter_list|()
block|{
return|return
name|var_name
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|getLine
parameter_list|()
block|{
return|return
name|line
return|;
block|}
name|LocalVariableGen
name|getLocalVariable
parameter_list|()
block|{
return|return
name|local_var
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
name|getType
parameter_list|()
block|{
return|return
name|name
operator|.
name|getType
argument_list|()
return|;
block|}
name|void
name|setLocalVariable
parameter_list|(
specifier|final
name|LocalVariableGen
name|local_var
parameter_list|)
block|{
name|this
operator|.
name|local_var
operator|=
name|local_var
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
if|if
condition|(
operator|!
name|reserved
condition|)
block|{
return|return
name|var_name
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
return|return
name|var_name
operator|+
literal|"<reserved key word>"
return|;
block|}
block|}
end_class

end_unit

