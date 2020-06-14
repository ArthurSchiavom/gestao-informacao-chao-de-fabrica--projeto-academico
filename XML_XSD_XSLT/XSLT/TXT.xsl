<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" indent="yes"/>

    <!-- Elaborado por mim e testado manualmente no http://xsltransform.net/ -->
    <xsl:template match="/"><xsl:apply-templates select="*"/>
    </xsl:template>

    <!--    Template que vai buscar tudo -->
    <xsl:template match="*">
        <xsl:param name = "indent" />
<!--        Escreve uma nova linha e indenta com o parametro indent-->
        <xsl:text>&#xa;</xsl:text><xsl:value-of select="$indent"/>
        <xsl:value-of select="concat(upper-case(substring(local-name(),1,1)),substring(local-name(), 2),' '[not(last())])"/>
        <xsl:choose>
<!--           Envia por parametro uma nova indentação no parametro "indent"-->
            <xsl:when test="count(*)>0"> -> {<xsl:apply-templates select="*"><xsl:with-param name="indent" select="concat($indent,'&#x9;')" /></xsl:apply-templates>
                <xsl:text>&#xa;</xsl:text><xsl:value-of select="$indent"/>}</xsl:when>
            <xsl:otherwise>: <xsl:value-of select="."/></xsl:otherwise>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>