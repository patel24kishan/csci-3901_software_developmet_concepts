#1.1
select count(TerritoryDescription) 
FROM territories
group by RegionID ;

#1.2
select * from products where ReorderLevel!=0;

#1.3
select * from orders where ShippedDate is null;

#1.4
select c.CustomerID,c.City,o.ShipCity
from customers c inner join orders o 
on c.CustomerID=o.CustomerID
where c.City!=o.ShipCity;

#2.1
select count(OrderID) 
FROM orders
group by ShipVia;

#2.2
select o.EmployeeID,count(CustomerID)
from orders o
WHERE o.OrderDate >= '1998-01-01' AND o.OrderDate <= '1998-03-31'
group by o.EmployeeID;

#2.3
select OrderID,ProductID,sum(UnitPrice*Quantity) as totalPrice from orderdetails
where OrderID=10256;

#2.4

create view PriceList
	as select UnitPrice*Quantity as price from orderdetails;
select count(OrderID) 
FROM orders o
WHERE o.OrderDate >= '1997-01-01' AND o.OrderDate <= '1997-12-31'
group by ShipVia;