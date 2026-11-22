<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<fmt:setLocale value="ru"/>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>${service.serviceName}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header>
  <h1>${service.serviceName}</h1>
</header>

<main>
  <%-- 1. Фильтр по тарифу и поиск --%>
  <form class="filter" method="get" action="drivers">
    <select name="tariff">
      <option value="">Все тарифы</option>
      <c:forEach var="t" items="${service.tariffs}">
        <option value="${t}" ${t == param.tariff ? 'selected' : ''}>${t}</option>
      </c:forEach>
    </select>
    <input type="text" name="q" placeholder="Поиск..." value="${fn:escapeXml(param.q)}">
    <label><input type="checkbox" name="all" ${param.all != null ? 'checked' : ''}> в том числе занятые</label>
    <button type="submit">Найти</button>
    <a href="drivers">Сбросить</a>
  </form>

  <%-- 2. Статистика по найденным водителям --%>
  <section class="cards">
    <div class="card">
      <span>Найдено водителей</span>
      <b>${stats.count}</b>
      <small>из них свободны: ${stats.available}</small>
    </div>
    <div class="card">
      <span>Средний рейтинг</span>
      <b><fmt:formatNumber value="${stats.averageRating}" minFractionDigits="2" maxFractionDigits="2"/></b>
      <small>от <fmt:formatNumber value="${stats.minRating}" minFractionDigits="2"/>
             до <fmt:formatNumber value="${stats.maxRating}" minFractionDigits="2"/></small>
    </div>
    <div class="card">
      <span>Средняя стоимость поездки</span>
      <b><fmt:formatNumber value="${stats.averageFare}" minFractionDigits="2" maxFractionDigits="2"/> ${service.currency}</b>
      <small>по ${stats.totalTrips} поездкам</small>
    </div>
    <div class="card">
      <span>Лучший водитель</span>
      <b>${stats.best != null ? stats.best.name : '-'}</b>
      <small>${stats.best.car}</small>
    </div>
  </section>

  <%-- 3. Таблица водителей --%>
  <table>
    <thead>
    <tr><th>Водитель</th><th>Автомобиль</th><th>Тариф</th><th>Рейтинг</th>
        <th>Поездок</th><th>Средняя стоимость</th><th>Статус</th></tr>
    </thead>
    <tbody>
    <c:forEach var="d" items="${drivers}">
      <tr>
        <td>${d.name}</td>
        <td>${d.car}</td>
        <td>${d.tariff}</td>
        <td><fmt:formatNumber value="${d.rating}" minFractionDigits="2"/></td>
        <td>${d.trips}</td>
        <td><fmt:formatNumber value="${d.averageFare}" minFractionDigits="2" maxFractionDigits="2"/> ${service.currency}</td>
        <td><span class="${d.available ? 'free' : 'busy'}">${d.available ? 'свободен' : 'занят'}</span></td>
      </tr>
    </c:forEach>
    <c:if test="${empty drivers}">
      <tr><td colspan="7" class="empty">Простите, никого не нашлось...</td></tr>
    </c:if>
    </tbody>
  </table>

  <%-- 4. Отчёт по всем водителям --%>
  <h2>Отчёт по всем водителям</h2>
  <div class="report">
    <table>
      <thead>
      <tr><th>Тариф</th><th>Водителей</th><th>Средний рейтинг</th><th>Поездок</th><th>Средняя стоимость</th></tr>
      </thead>
      <tbody>
      <c:forEach var="row" items="${report}">
        <tr>
          <td>${row.key}</td>
          <td>${row.value.count}</td>
          <td><fmt:formatNumber value="${row.value.averageRating}" minFractionDigits="2" maxFractionDigits="2"/></td>
          <td>${row.value.totalTrips}</td>
          <td><fmt:formatNumber value="${row.value.averageFare}" minFractionDigits="2" maxFractionDigits="2"/> ${service.currency}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>

    <div class="rating">
      <h3>Статистика по рейтингу</h3>
      <c:forEach var="g" items="${ratingGroups}">
        <div class="bar-row">
          <span>${g.key}</span>
          <div class="bar"><i style="width: ${g.value * 100 / fn:length(service.allDrivers)}%"></i></div>
          <b>${g.value}</b>
        </div>
      </c:forEach>
    </div>
  </div>

  <p><a class="button" href="report">Скачать весь список в CSV</a></p>
</main>
</body>
</html>
