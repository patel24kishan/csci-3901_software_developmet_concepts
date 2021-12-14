# ****************Practice****************
select * from products;
select c.CompanyName AS customer_Name from customers c join orders o on c.CustomerID=o.CustomerID
 WHERE (o.OrderDate between '1998-03-01' AND  '1998-03-05');
# ****************************************

# ************Supplier Details************
with product_sale_info as
(
SELECT  distinct ct.CategoryID, 
    ct.CategoryName,  
    pd.ProductName, 
    sup.CompanyName AS Supplier_Name,
    sum(od.Quantity) AS NumOfProducts,
    sum(round(od.UnitPrice * od.Quantity * (1 - od.Discount), 2)) as Total_Sales
FROM         orders o
INNER JOIN orderdetails od ON o.OrderID=od.OrderID
INNER JOIN products pd ON od.ProductID=pd.ProductID
INNER JOIN categories ct ON ct.CategoryID=pd.CategoryID
INNER JOIN suppliers sup ON sup.SupplierID=pd.SupplierID
WHERE (o.OrderDate between '1998-01-09' AND  '1998-01-13') #AND sup.CompanyName="Aux joyeux ecclsiastiques"
group by ct.CategoryID,ct.CategoryName,pd.ProductName
order by ct.CategoryID,pd.ProductName,Total_sales desc
)
 select psi.Supplier_Name,
        s.Address,s.City,
        s.Region,s.PostalCode,s.Country,
        psi.NumOfProducts AS num_order, 
        sum(psi.Total_Sales) AS TotalSales_$
from product_sale_info psi
join suppliers s on psi.Supplier_Name=s.CompanyName
group by psi.Supplier_Name#,psi.ProductName
order by psi.Supplier_Name 
;
##########################################
describe customers;
# ************Customer Details************
SELECT    c.CompanyName AS customer_name
		  ,c.Address AS street
          ,c.City AS city
          ,c.Region AS region
          ,c.PostalCode AS postal_code
          ,c.Country AS country
		  ,count(distinct o.OrderID) AS num_order
		  ,sum(od.Quantity*od.UnitPrice) AS order_value
		
FROM       orderdetails od
INNER JOIN orders o ON o.OrderID=od.OrderID
INNER JOIN customers c ON o.CustomerID=c.CustomerID
WHERE o.OrderDate between '1998-01-09' AND  '1998-01-13'
group by c.CompanyName,c.CustomerID
;

# ************Product Details************
SELECT  distinct ct.CategoryID, 
    ct.CategoryName,  
    pd.ProductName, 
    sup.CompanyName AS Supplier_Name,
    sum(od.Quantity) AS Unit_Sold,
    sum(round(od.UnitPrice * od.Quantity * (1 - od.Discount), 2)) as TotalSales_$
FROM         orders o
INNER JOIN orderdetails od ON o.OrderID=od.OrderID
INNER JOIN products pd ON od.ProductID=pd.ProductID
INNER JOIN categories ct ON ct.CategoryID=pd.CategoryID
INNER JOIN suppliers sup ON sup.SupplierID=pd.SupplierID
WHERE (o.OrderDate between '1998-01-09' AND  '1998-01-13') #AND sup.CompanyName="Exotic Liquids"
group by ct.CategoryID,ct.CategoryName,pd.ProductName
order by ct.CategoryID,pd.ProductName,TotalSales_$ desc
;
##########################################
