<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<section id="main" class="small-padding">
    <header class="page-header">
        <div class="container">
            <h1 class="title">Приобретение абонемента</h1>
        </div>
    </header>

    <div class="container">
        <div class="row">
            <article class="content col-sm-12 col-md-12">
                <div class="table-box">
                    <table id="shopping-cart-table" class="shopping-cart-table table table-bordered table-striped">
                        <thead>
                        <tr>
                            <th></th>
                            <th class="td-name">Название абонемента</th>
                            <th class="td-price">Цена</th>
                            <th class="td-qty">Количество</th>
                            <th class="td-total">Итого</th>
                            <th class="td-remove"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="product" items="${products}">
                        <tr>
                            <td class="td-images">
                                <a href="${contextPath}/products/${product.id}" class="product-image">
                                    <img src="${contextPath}/uploaded/images/${event.logo}" alt="" title="" width="70" height="70">
                                </a>
                            </td>
                            <td class="td-name">
                                <h2 class="product-name">
                                    <a href="${contextPath}/products/${product.id}">${product.name}</a>
                                </h2>
                            </td>
                            <td class="td-price">
                                <div class="price">${product.price}</div>
                            </td>
                            <td class="td-qty">
                                <input class="form-control" type="text" value="1">
                            </td>
                            <td class="td-total">
                                <div class="price">$750.00</div>
                            </td>
                            <td class="td-remove">
                                <a href="${contextPath}/cart/remove/${product.id}" class="product-remove">
                                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
                                         x="0px" y="0px" width="16px" height="16px" viewBox="0 0 16 16"
                                         enable-background="new 0 0 16 16" xml:space="preserve">
					  <g>
                          <path fill="#7f7f7f"
                                d="M6,13c0.553,0,1-0.447,1-1V7c0-0.553-0.447-1-1-1S5,6.447,5,7v5C5,12.553,5.447,13,6,13z"></path>
                          <path fill="#7f7f7f"
                                d="M10,13c0.553,0,1-0.447,1-1V7c0-0.553-0.447-1-1-1S9,6.447,9,7v5C9,12.553,9.447,13,10,13z"></path>
                          <path fill="#7f7f7f" d="M14,3h-1V1c0-0.552-0.447-1-1-1H4C3.448,0,3,0.448,3,1v2H2C1.447,3,1,3.447,1,4s0.447,1,1,1
						c0,0.273,0,8.727,0,9c0,1.104,0.896,2,2,2h8c1.104,0,2-0.896,2-2c0-0.273,0-8.727,0-9c0.553,0,1-0.447,1-1S14.553,3,14,3z M5,2h6v1
						H5V2z M12,14H4V5h8V14z"></path>
                      </g>
					</svg>
                                </a><!-- .product-remove -->
                            </td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <table class="shopping-cart-table shopping-cart-table-button table">
                    <tbody>
                    <tr>
                        <td class="action no-border">
                            <a href="${contextPath}/products"><i class="fa fa-angle-left"></i> Продолжить покупки</a>
                            <a href="${contextPath}/cart" class="update"><i class="fa fa-rotate-right"></i> Обновить карзину</a>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <div id="car-bottom" class="row">
                    <div class="col-sm-12 col-md-offset-8 col-md-4">
                        <div class="car-bottom-box bg total">
                            <table>
                                <tbody>
                                <tr>
                                    <td>Итого</td>
                                    <td><span class="price">$1,500.00</span></td>
                                </tr>
                                </tbody>
                            </table>
                            <div>
                                <a href="${contextPath}/cart/buy" class="btn checkout btn-default btn-lg">Купить</a>
                            </div>
                        </div>
                    </div>
                </div>

            </article>
        </div>
    </div>
</section>