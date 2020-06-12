<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    version="3.0">
    
<!--___________________________
    &#x9; = Tab
    &#x10; = New Line
____________________________-->
    
    <xsl:output method="text"/>
    
    <xsl:template match="/">{
    "chaoDeFabrica":
    {
        <xsl:for-each select="./*/*">
            <text></text>"<xsl:value-of select="local-name()"/>": "<xsl:value-of select="count(./*)"/>"
        </xsl:for-each>
    }
}
    </xsl:template>
    
</xsl:stylesheet>