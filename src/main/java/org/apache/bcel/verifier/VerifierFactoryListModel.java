begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.   *  */
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ListModel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ListDataEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ListDataListener
import|;
end_import

begin_comment
comment|/**  * This class implements an adapter; it implements both a Swing ListModel and  * a VerifierFactoryObserver.  *  * @version $Id$  * @author Enver Haase  */
end_comment

begin_class
specifier|public
class|class
name|VerifierFactoryListModel
implements|implements
name|VerifierFactoryObserver
implements|,
name|ListModel
argument_list|<
name|String
argument_list|>
block|{
specifier|private
name|List
argument_list|<
name|ListDataListener
argument_list|>
name|listeners
init|=
operator|new
name|ArrayList
argument_list|<
name|ListDataListener
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|String
argument_list|>
name|cache
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|VerifierFactoryListModel
parameter_list|()
block|{
name|VerifierFactory
operator|.
name|attach
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|update
argument_list|(
literal|null
argument_list|)
expr_stmt|;
comment|// fill cache.
block|}
specifier|public
specifier|synchronized
name|void
name|update
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|int
name|size
init|=
name|listeners
operator|.
name|size
argument_list|()
decl_stmt|;
name|Verifier
index|[]
name|verifiers
init|=
name|VerifierFactory
operator|.
name|getVerifiers
argument_list|()
decl_stmt|;
name|int
name|num_of_verifiers
init|=
name|verifiers
operator|.
name|length
decl_stmt|;
name|cache
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|Verifier
name|verifier
range|:
name|verifiers
control|)
block|{
name|cache
operator|.
name|add
argument_list|(
name|verifier
operator|.
name|getClassName
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|ListDataListener
name|listener
range|:
name|listeners
control|)
block|{
name|ListDataEvent
name|e
init|=
operator|new
name|ListDataEvent
argument_list|(
name|this
argument_list|,
name|ListDataEvent
operator|.
name|CONTENTS_CHANGED
argument_list|,
literal|0
argument_list|,
name|num_of_verifiers
operator|-
literal|1
argument_list|)
decl_stmt|;
name|listener
operator|.
name|contentsChanged
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|synchronized
name|void
name|addListDataListener
parameter_list|(
name|ListDataListener
name|l
parameter_list|)
block|{
name|listeners
operator|.
name|add
argument_list|(
name|l
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|synchronized
name|void
name|removeListDataListener
parameter_list|(
name|ListDataListener
name|l
parameter_list|)
block|{
name|listeners
operator|.
name|remove
argument_list|(
name|l
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|synchronized
name|int
name|getSize
parameter_list|()
block|{
return|return
name|cache
operator|.
name|size
argument_list|()
return|;
block|}
specifier|public
specifier|synchronized
name|String
name|getElementAt
parameter_list|(
name|int
name|index
parameter_list|)
block|{
return|return
operator|(
name|cache
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|cache
operator|.
name|size
argument_list|()
index|]
argument_list|)
operator|)
index|[
name|index
index|]
return|;
block|}
block|}
end_class

end_unit

