<%@ include file="/WEB-INF/templates/mymodule/includes/taglibs.jsp"%>

<h1>Customer details</h1>

<form:form action="?" commandName="customer" method="POST">
    <blossom:pecid-input />
    <table>
        <tr>
            <td>First name</td>
            <td><form:input path="firstName" cssClass="textinput" /></td>
        </tr>
        <tr>
            <td>Last name</td>
            <td><form:input path="lastName" cssClass="textinput" /></td>
        </tr>
        <tr>
            <td>Address Line 1</td>
            <td><form:input path="addressLine1" cssClass="textinput" /></td>
        </tr>
        <tr>
            <td>Address Line 2</td>
            <td><form:input path="addressLine2" cssClass="textinput" /></td>
        </tr>
        <tr>
            <td>Phone Number</td>
            <td><form:input path="phoneNumber" cssClass="textinput" /></td>
        </tr>
        <tr>
            <td>E-Mail</td>
            <td><form:input path="email" cssClass="textinput" /></td>
        </tr>
        <tr>
            <td colspan="2" align="right"><input type="submit" value="Place order" /></td>
        </tr>
    </table>
</form:form>
