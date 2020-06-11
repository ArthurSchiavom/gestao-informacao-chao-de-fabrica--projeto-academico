<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text" indent="yes"/>

<!-- Elaborado por mim e testado manualmente no http://xsltransform.net/ -->

<xsl:template match="/">Chão de Fábrica<xsl:for-each select="child::*/*">
    <!-- Quando o elemento tem valores lá dentro, se não tiver, não escreve nada-->
    <!-- O Select converte o nome indicado no XML num nome com capitalização na primeira letra-->
    <xsl:if test="count(*) > 0"><xsl:text>&#xa;&#x9;</xsl:text><xsl:value-of select="concat(upper-case(substring(local-name(),1,1)),
            substring(local-name(), 2),
            ' '[not(last())]
        )"/>
        <!-- Vai buscar um elemento da lista xml individual -->
        <xsl:for-each select="child::*"><xsl:text>&#xa;&#x9;&#x9;</xsl:text><!-- Verifica se tem atributo e se tiver escreve no titulo do elemento -->
            <xsl:choose>
            <xsl:when test="count(attribute::*)=1"><xsl:value-of select="attribute::*"/></xsl:when>
            <xsl:otherwise><xsl:value-of select="concat(upper-case(substring(local-name(),1,1)),substring(local-name(), 2),' '[not(last())])"/></xsl:otherwise>
            </xsl:choose><xsl:apply-templates select="*" mode="Elemento"/><!-- Chama o template que trata dos value objects de cada elemento de dominio-->
        </xsl:for-each>
    </xsl:if>
</xsl:for-each>
</xsl:template>

<xsl:template match="*" mode="Elemento">
    <xsl:text>&#xa;&#x9;&#x9;&#x9;</xsl:text>
    <xsl:value-of select="concat(upper-case(substring(local-name(),1,1)),substring(local-name(), 2),' '[not(last())])"/>
    <!-- Verifica se o elemento tem mais subelementos, se tiver chama outra fez este template de forma recursiva até chegar ao ultimo-->
    <xsl:choose>
        <xsl:when test="count(*)>0"> - [<xsl:apply-templates select="*" mode="Elemento"/>&#xa;&#x9;&#x9;&#x9;]</xsl:when>
        <xsl:otherwise>: <xsl:value-of select="."/></xsl:otherwise>
    </xsl:choose>
</xsl:template>

</xsl:stylesheet>