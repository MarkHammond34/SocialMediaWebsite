-- Tag Table Updates
ALTER TABLE honeycomb.tag DROP PRIMARY KEY, ADD PRIMARY KEY(tag, similar_tag);
ALTER TABLE honeycomb.tag add column count INT not null default '1';
ALTER TABLE honeycomb.tag add column last_incremented timestamp not null default 'CURRENT_TIME';

-- Sample Tags and Similar Tags from the Sample Posts
INSERT INTO `honeycomb`.`tag` (`tag`, `similar_tag`) VALUES ('DIY', 'WOODEN');
INSERT INTO `honeycomb`.`tag` (`tag`, `similar_tag`) VALUES ('CHRISTMAS', 'BAKING');
INSERT INTO `honeycomb`.`tag` (`tag`, `similar_tag`) VALUES ('FOOTBALL', 'CLOTHING');
INSERT INTO `honeycomb`.`tag` (`tag`, `similar_tag`) VALUES ('GAMING', 'SETUP');
INSERT INTO `honeycomb`.`tag` (`tag`, `similar_tag`) VALUES ('HOLIDAY', 'DECOR');
INSERT INTO `honeycomb`.`tag` (`tag`, `similar_tag`) VALUES ('HOME', 'OFFICE');
