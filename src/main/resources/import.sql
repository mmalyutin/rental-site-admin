INSERT INTO properties(id, name, geo_coordinates, feature_list, zoomlevel)
    VALUES (1,'Cape Cod Cottage', ST_SetSRID((ST_MakePoint(41.652013, -70.149837)), 4326),'{Oceanview,Pool,"Exercise room",Internet/WIFI,Cable,"2 bedrooms","Sunroom w/ sleeper sofa",Washer/Dryer,"2 weeks minimum stay"}',0);

INSERT INTO properties(id, name, geo_coordinates, feature_list, zoomlevel)
    VALUES (2,'Lake House',ST_SetSRID((ST_MakePoint(43.124026,-74.140129)), 4326),'{"right of way to lake"}',0);

INSERT INTO properties(id, name, geo_coordinates, feature_list, zoomlevel)
    VALUES (3,'White House',ST_SetSRID((ST_MakePoint(38.897603, -77.036665)), 4326),'{"Central Location","Large Staff","Excellent Security","4 year rentals only","His and Her Office Wings"}',2);

INSERT INTO properties(id, name, geo_coordinates, feature_list, zoomlevel)
    VALUES (4,'Florida Condo',ST_SetSRID((ST_MakePoint(27.271112, -82.561622)), 4326),'{"Close to white sand beach on Gulf coast","Nearby restaurants"}',1);