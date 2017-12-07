/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jena.sdb.layout1;

import org.apache.jena.sdb.compiler.QuadBlockCompiler ;
import org.apache.jena.sdb.compiler.QuadBlockCompilerMain ;
import org.apache.jena.sdb.compiler.QueryCompilerMain ;
import org.apache.jena.sdb.compiler.SlotCompiler ;
import org.apache.jena.sdb.core.SDBRequest ;
import org.apache.jena.sparql.algebra.Op ;

public class QueryCompiler1 extends QueryCompilerMain 
{
    private EncoderDecoder codec = null ;
    
    public QueryCompiler1(SDBRequest request, EncoderDecoder codec)
    { 
        super(request) ; 
        this.codec = codec ;
    }
    
    @Override
    public QuadBlockCompiler createQuadBlockCompiler()
    { 
        SlotCompiler sComp = new SlotCompiler1(request, codec) ;
        return new QuadBlockCompilerMain(request, sComp) ; 
    }

    @Override
    protected Op postProcessSQL(Op op)
    {
        // (slice (distinct ....))
        op = rewriteDistinct(op, request) ;
        op = rewriteLimitOffset(op, request) ;
        return op ;
    }
}
