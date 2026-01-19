-- ==============================
-- USERS
-- ==============================
INSERT INTO users (name)
VALUES ('Demo User')
ON CONFLICT DO NOTHING;

-- ==============================
-- TASK CATEGORY
-- ==============================
INSERT INTO task_category (
    name,
    user_id
)
VALUES (
    'Health',
    (SELECT id FROM users WHERE name = 'Demo User')
)
ON CONFLICT DO NOTHING;

-- ==============================
-- TASK
-- ==============================
INSERT INTO task (
    title,
    description,
    category_id,
    user_id
)
VALUES (
    'Morning Walk',
    '30 minutes morning walk',
    (SELECT id FROM task_category WHERE name = 'Health'),
    (SELECT id FROM users WHERE name = 'Demo User')
)
ON CONFLICT DO NOTHING;

-- ==============================
-- HABIT
-- ==============================
INSERT INTO habit (
    name,
    description,
    user_id
)
VALUES (
    'Daily Exercise',
    'Workout at least 30 minutes',
    (SELECT id FROM users WHERE name = 'Demo User')
)
ON CONFLICT DO NOTHING;

-- ==============================
-- HABIT LOG
-- ==============================
INSERT INTO habit_log (
    habit_id,
    log_date,
    completed
)
VALUES (
    (SELECT id FROM habit WHERE name = 'Daily Exercise'),
    CURRENT_DATE,
    true
)
ON CONFLICT DO NOTHING;
