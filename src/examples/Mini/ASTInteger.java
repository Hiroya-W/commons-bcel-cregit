begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright  2000-2004 The Apache Software Foundation  *  *  Licensed under the Apache License, Version 2.0 (the "License");   *  you may not use this file except in compliance with the License.  *  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.   *  */
end_comment

begin_comment
comment|/* Generated By:JJTree: Do not edit this line. ASTInteger.java */
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
name|PUSH
import|;
end_import

begin_comment
comment|/**  *  * @version $Id$  * @author<A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>  */
end_comment

begin_class
specifier|public
class|class
name|ASTInteger
extends|extends
name|ASTExpr
block|{
specifier|private
name|int
name|value
decl_stmt|;
comment|// Generated methods
name|ASTInteger
parameter_list|(
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
name|ASTInteger
parameter_list|(
name|MiniParser
name|p
parameter_list|,
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
name|MiniParser
name|p
parameter_list|,
name|int
name|id
parameter_list|)
block|{
return|return
operator|new
name|ASTInteger
argument_list|(
name|p
argument_list|,
name|id
argument_list|)
return|;
block|}
comment|// closeNode, dump inherited from Expr
comment|/**    * @return identifier and line/column number of appearance    */
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|super
operator|.
name|toString
argument_list|()
operator|+
literal|" = "
operator|+
name|value
return|;
block|}
comment|/**    * Overrides ASTExpr.traverse()    */
specifier|public
name|ASTExpr
name|traverse
parameter_list|(
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
return|return
name|this
return|;
comment|// Nothing to reduce/traverse here
block|}
comment|/**    * Second pass    * Overrides AstExpr.eval()    * @return type of expression    */
specifier|public
name|int
name|eval
parameter_list|(
name|int
name|expected
parameter_list|)
block|{
name|is_simple
operator|=
literal|true
expr_stmt|;
comment|// (Very) simple expression, always true
return|return
name|type
operator|=
name|T_INT
return|;
block|}
comment|/**    * Fourth pass, produce Java code.    */
specifier|public
name|void
name|code
parameter_list|(
name|StringBuffer
name|buf
parameter_list|)
block|{
name|ASTFunDecl
operator|.
name|push
argument_list|(
name|buf
argument_list|,
literal|""
operator|+
name|value
argument_list|)
expr_stmt|;
block|}
comment|/**    * Fifth pass, produce Java byte code.    */
specifier|public
name|void
name|byte_code
parameter_list|(
name|InstructionList
name|il
parameter_list|,
name|MethodGen
name|method
parameter_list|,
name|ConstantPoolGen
name|cp
parameter_list|)
block|{
name|il
operator|.
name|append
argument_list|(
operator|new
name|PUSH
argument_list|(
name|cp
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
name|ASTFunDecl
operator|.
name|push
argument_list|()
expr_stmt|;
block|}
name|void
name|setValue
parameter_list|(
name|int
name|value
parameter_list|)
block|{
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
block|}
name|int
name|getValue
parameter_list|()
block|{
return|return
name|value
return|;
block|}
block|}
end_class

end_unit
