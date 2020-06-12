<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text"/>
<!-- Baseado no http://www.bizcoder.com/convert-xml-to-json-using-xslt com modificações para servir para o UC
e no guia https://www.json.org/json-en.html, o resultado deste JSON_A foi validado manualmente no https://jsonformatter.curiousconcept.com/
e é testado no java com o JSON_A API http://maven-repository.com/artifact/org.json/json -->

<!-- Usa o apply-templates para escolher qual o template mais apropriado -->
<xsl:template match="/">{<xsl:apply-templates select="*"/>}
</xsl:template>

<!-- Para um elemento de xml, faz match a qualquer coisa, vai buscar o nome do elemento e depois chama o template que toma conta dos sub elementos-->
<xsl:template match="*">"<xsl:value-of select="local-name()"/>" :<xsl:call-template name="SubElemento">
    <!-- Apaga o nome do parametro pai -->
    <xsl:with-param name="parente" select="'Yes'"> </xsl:with-param>
    </xsl:call-template>
</xsl:template>

<!-- Escreve todos os elementos que fazem parte de uma lista(por exemplo, materiais tem material no XML, este template trata do material) -->
<xsl:template match="*" mode="ElementoDeUmaLista"><xsl:call-template name="SubElemento"/>
</xsl:template>

<!-- Template para um sub elemento(um elemento contido dentro de outro elemento) -->
<xsl:template name="SubElemento">
    <xsl:param name="parente"/>
    <!-- Guarda o nome da lista numa variavel para poder fazer uma lista mais tarde-->
    <xsl:variable name="nomeLista" select="local-name(*[1])"/>
    <xsl:choose>            
        <!-- Imprime os elementos sem dependencias do xml, se for parente imprime o nome do elemento, se não for, imprime o conteudo -->
        <xsl:when test="not(*|@*)"><xsl:choose><xsl:when test="$parente='Yes'"> "<xsl:value-of select="."/>"</xsl:when>
                <xsl:otherwise>"<xsl:value-of select="local-name()"/>":"<xsl:value-of  select="."/>"</xsl:otherwise>
            </xsl:choose>           
        </xsl:when>
        <!-- Para quando se tem múltiplos elementos dentro de uma lista(por exemplo linhasProducao pode ter multiplas linhaProducao) -->                
        <xsl:when test="count(*[local-name()=$nomeLista]) > 1">{ "<xsl:value-of  select="$nomeLista"/>" :[<xsl:apply-templates select="*" mode="ElementoDeUmaLista"/>] }</xsl:when>
        <!-- Senão imprime o elemento que corresponder (ou atributo ou elemento)-->
        <xsl:otherwise>{<xsl:apply-templates select="@*"/><xsl:apply-templates select="*"/>}</xsl:otherwise>
    </xsl:choose>
    <xsl:if test="following-sibling::*">,</xsl:if>
</xsl:template>

<!-- Template para os atributos de xml, imprime o nome e o valor do atributo -->
<xsl:template match="@*">"<xsl:value-of select="local-name()"/>" : "<xsl:value-of select="."/>",</xsl:template>
</xsl:stylesheet>
