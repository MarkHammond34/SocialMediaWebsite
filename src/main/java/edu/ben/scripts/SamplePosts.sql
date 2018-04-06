INSERT INTO `honeycomb`.`post` (`postID`, `userID`, `title`, `description`, `link`, `active`, `draft`, `tack_count`, `like_count`, `view_count`, `created_on`, `image_path`) VALUES ('1', '1', 'DIY Wooden Desk', 'DIY Wooden Desk built for my new office.', 'https://www.decoist.com/2012-05-17/diy-desks/', '1', '0', '0', '0', '0', '2017-11-16 15:00:00', 'path');
INSERT INTO `honeycomb`.`post` (`postID`, `userID`, `title`, `description`, `link`, `active`, `draft`, `tack_count`, `like_count`, `view_count`, `created_on`, `image_path`) VALUES ('2', '2', 'Christmas Cookies', 'Delicious christmas cookies made from scratch.', 'http://www.goodhousekeeping.com/food-recipes/a6874/sugar-cookies-4477/', '1', '0', '0', '0', '0', '2017-11-16 16:00:00', 'path');
INSERT INTO `honeycomb`.`post` (`postID`, `userID`, `title`, `description`, `link`, `active`, `draft`, `tack_count`, `like_count`, `view_count`, `created_on`, `image_path`) VALUES ('3', '3', 'DIY Sports Shirts', 'DIY sports t-shirts made at home.', 'https://www.happinessishomemade.net/diy-sports-team-shirt-budget/', '1', '0', '0', '0', '0', '2017-11-16 17:00:00' , 'path');
INSERT INTO `honeycomb`.`post` (`postID`, `userID`, `title`, `description`, `link`, `active`, `draft`, `tack_count`, `like_count`, `view_count`, `created_on`, `image_path`) VALUES ('4', '4', 'Halo Gaming Setup', 'New $1,500 Halo gaming setup!', 'https://www.reddit.com/r/battlestations/comments/4qmfwz/triathlon_training_room_battlestation/', '1', '0', '0', '0', '0', '2017-11-16 20:20:00' , 'path');
INSERT INTO `honeycomb`.`post` (`postID`, `userID`, `title`, `description`, `link`, `active`, `draft`, `tack_count`, `like_count`, `view_count`, `created_on`, `image_path`) VALUES ('5', '3', 'Holiday Decorations', 'Trendy holiday decorations guarenteed to make everyone jealous.', 'http://www.countryliving.com/diy-crafts/tips/g907/craft-ideas-for-christmas-decorations-1209/', '1', '0', '0', '0', '0', '2017-11-16 20:30:00' , 'path');
INSERT INTO `honeycomb`.`post` (`postID`, `userID`, `title`, `description`, `link`, `active`, `draft`, `tack_count`, `like_count`, `view_count`, `created_on`, `image_path`) VALUES ('6', '1', 'Modern Home Office', 'Modern home office put together on a budget.', 'http://www.hgtv.com/design/rooms/other-rooms/desks-and-study-zones-pictures', '1', '0', '0', '0', '0', '2017-11-16 20:58:00' , 'path');

INSERT INTO `honeycomb`.`tag` (`tag`, `similar_tag`) VALUES ('DIY', 'WOODEN');

INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('1', 'DIY');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('1', 'WOODEN');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('1', 'DESK');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('1', 'HOME');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('1', 'OFFICE');

INSERT INTO `honeycomb`.`tag` (`tag`, `similar_tag`) VALUES ('CHRISTMAS', 'BAKING');

INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('2', 'DIY');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('2', 'CHRISTMAS');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('2', 'COOKIES');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('2', 'BAKING');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('2', 'FOOD');

INSERT INTO `honeycomb`.`tag` (`tag`, `similar_tag`) VALUES ('FOOTBALL', 'CLOTHING');

INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('3', 'DIY');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('3', 'SPORTS');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('3', 'SHIRT');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('3', 'CLOTHING');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('3', 'EASY');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('3', 'FOOTBALL');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('3', 'BASKETBALL');

INSERT INTO `honeycomb`.`tag` (`tag`, `similar_tag`) VALUES ('GAMING', 'SETUP');

INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('4', 'DIY');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('4', 'HALO');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('4', 'GAMING');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('4', 'SETUP');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('4', 'COOL');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('4', 'HOME');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('4', 'EXPENSIVE');

INSERT INTO `honeycomb`.`tag` (`tag`, `similar_tag`) VALUES ('HOLIDAY', 'DECOR');

INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('5', 'HOLIDAY');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('5', 'DECOR');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('5', 'DECORATIONS');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('5', 'HOME');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('5', 'CHRISTMAS');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('5', 'TREES');

INSERT INTO `honeycomb`.`tag` (`tag`, `similar_tag`) VALUES ('HOME', 'OFFICE');

INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('6', 'MODERN');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('6', 'HOME');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('6', 'OFFICE');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('6', 'FOR MEN');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('6', 'WOODEN');
INSERT INTO `honeycomb`.`post_tag` (`postID`, `tag`) VALUES ('6', 'COOL');