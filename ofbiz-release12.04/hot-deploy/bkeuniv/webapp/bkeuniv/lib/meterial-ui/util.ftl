

<#assign bits=32 />
<#assign m_w="0.${.now?long?string}"?number />
<#assign m_z="0.${turnoverstr(.now?long?string)}"?number />


<#function random min max >
    <#local h= intdectobin(65535) />
    <#local mzh= fdectobin("0.${turnoverstr(.now?long?string)}"?number) />
    <#local mwh= fdectobin(m_w) />
    <#local z= 36969 * intbintodec(andmultiply(mzh, h)) + intbintodec( shiftr(mzh, bits/2) ) />;
    <#local w= 18000 * intbintodec(andmultiply(mwh, h)) + intbintodec( shiftr(mwh, bits/2) ) />;
    <#local r= intbintodec( shiftl( intdectobin(z), bits/2 ) ) + w />
    <#local m_w=m_z />
    <#local m_z=("0." + turnoverstr(r?string))?number />
    <#return (m_z*(max - min) + min + 1)?floor/>
</#function>

<#function turnoverstr str >
    <#local l = str?length />
    <#local r = ""/>
    <#list 1..l as i>
        <#local r = r+str?substring(l-i,l-i+1)/>
    </#list>
    <#return r/>
</#function>

<#function shiftl b n >
    <#local l = b?length />
    <#local r = ""?left_pad(bits, "0")/>
    <#if (l > n)>
        <#local h = b?substring(n,l)/>
        <#local r = h + ""?right_pad(n, "0")/>
    </#if>
    <#return r />
</#function>

<#function shiftr b n >
    <#local l = b?length />
    <#local r = ""?left_pad(bits, "0")/>
    <#if (l > n)>
        <#local h = b?substring(0,l-n)/>
        <#local r = ""?left_pad(n, "0") + h/>
    </#if>
    <#return r />
</#function>

<#function fbintodec b >
    <#local h = 2 />
    <#local r = 0 />
    <#local l = b?length />
    <#list 0..l-1 as i>
        <#if b?substring(i, i+1) == "1" >
            <#local r = r + 1/h />
        </#if>
        <#local h = h * 2 />
    </#list>
    <#return r />
</#function>

<#function fdectobin d >
    <#local r = ""/>
    <#local h = 0/>
    <#list 0..bits-1 as i>
        <#local h = d*2 />
        <#if h == 1>
            <#local r = (r + "1")?right_pad(bits, "0")/>
            <#break/>
        <#elseif (h > 1) >
            <#local r = (r + "1")/>
            <#local d = h-1 />
        <#else>
            <#local d = h />
            <#local r = (r + "0")/>
        </#if>
    </#list>
    <#local r = r?right_pad(bits, "0")/>
    <#return r />
</#function>

<#function intdectobin d >
    <#local r = ""/>
    <#local h = 0/>
    <#list 0..bits as i>
        <#local hh = d%2 />
        <#local h = (d/2)?int />
        <#local d = d - h />
        <#if h == 0>
            <#local r = (hh?string + r )?left_pad(bits, "0")/>
            <#break/>
        <#else> 
            <#local r = hh?string + r/>
        </#if>
    </#list>
    <#local r = r?left_pad(bits, "0")/>
    <#return r />
</#function>

<#function intbintodec b >
    <#local h = 1 />
    <#local r = 0 />
    <#local l = b?length />
    <#list 1..l as i>
        <#if b?substring(l-i, l-i+1) == "1" >
            <#local r = r + h />
        </#if>
        <#local h = h * 2 />
    </#list>
    <#return r />
</#function>

<#function andmultiply a b >
    <#local a = a?left_pad(bits, "0")/>
    <#local b = b?left_pad(bits, "0")/>
    <#local r = "" />
    <#local l = a?length />
    <#list 0..l-1 as i>
        <#if a?substring(i, i+1) == b?substring(i, i+1) >
            <#local r = (r + "1")/>
        <#else>
            <#local r = (r + "0")/>
        </#if>
    </#list>
    <#return r />
</#function>

