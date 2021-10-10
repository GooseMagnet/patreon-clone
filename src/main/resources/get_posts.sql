SELECT
    DISTINCT(post.id),
    post.type,
    post.page_id,
    SUBSTRING(content,1,20)
FROM
    post,
    page,
    pledge,
    post_tiers,
    reward_tier
WHERE
    pledge.patron_id = 470 AND
    pledge.page_id = 227 AND
    post.page_id = pledge.page_id AND
    pledge.pledge_amount >= reward_tier.price AND
    reward_tier.page_id = pledge.page_id AND
    post.id = post_tiers.post_id OR
    (
        post.page_id = 227 AND
        post.id NOT IN (SELECT post_id from post_tiers)
    )
LIMIT 3;


-- Get all posts on a page that a user pledged to see
SELECT
    reward_tier.id rt_id,
    reward_tier.price,
    reward_tier.title,
    pledge.id plg_id,
    pledge.pledge_amount,
    pledge.page_id,
    pledge.patron_id
FROM reward_tier,pledge
WHERE
    pledge.patron_id = 470 AND
    pledge.page_id = 227 AND
    pledge.pledge_amount >= reward_tier.price AND
    pledge.page_id = reward_tier.page_id;


-- Get all public posts
SELECT
    id,
    type,
    page_id
FROM
    post
WHERE
    post.page_id = 227 AND
    post.id NOT IN (select post_id from post_tiers);


SELECT
    post_tiers.post_id,
    reward_tier.id rt_id,
    reward_tier.price,
    reward_tier.title,
    pledge.id plg_id,
    pledge.pledge_amount,
    pledge.page_id,
    pledge.patron_id
FROM
    reward_tier,
    pledge,post_tiers
WHERE
    pledge.patron_id = 470 AND
    pledge.page_id = 227 AND
    pledge.pledge_amount >= reward_tier.price AND
    pledge.page_id = reward_tier.page_id AND
    post_tiers.reward_tier_id = reward_tier.id;                                                                                                      st_tiers.reward_tier_id = reward_tier.id;


--   reward_tier.id rt_id,
--   reward_tier.price,
--   reward_tier.title,
--   pledge.id plg_id,
--   pledge.pledge_amount,
--   pledge.page_id,
--   pledge.patron_id
SELECT
    id,
    type,
    content,
    page_id
FROM
    post
WHERE
    id IN (
SELECT
   post_tiers.post_id
FROM
   reward_tier,
   pledge,
   post_tiers
WHERE
   pledge.patron_id = 470
   AND pledge.page_id = 227
   AND pledge.pledge_amount >= reward_tier.price
   AND pledge.page_id = reward_tier.page_id
   AND post_tiers.reward_tier_id = reward_tier.id
UNION
(SELECT
   id
FROM
   post
WHERE
   post.page_id = 227
   AND post.id NOT IN
   (
      select
         post_id
      from
         post_tiers
   )
    )
);
