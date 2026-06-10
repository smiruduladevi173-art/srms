import sqlite3
import pandas as pd
import matplotlib.pyplot as plt

# ==========================================
# DATABASE
# ==========================================

conn = sqlite3.connect(
    "database/srms.db"
)


query = """
SELECT
    s.name,
    AVG(m.marks) AS average_marks
FROM students s
JOIN marks m
    ON s.student_id = m.student_id
GROUP BY s.student_id
ORDER BY average_marks DESC
LIMIT 10
"""

df = pd.read_sql_query(
    query,
    conn
)

conn.close()

# ==========================================
# EMPTY DATA SAFETY
# ==========================================

if len(df) == 0:

    plt.figure(
        figsize=(12, 6)
    )

    plt.text(
        0.5,
        0.5,
        "No Student Data Available",
        fontsize=18,
        ha="center",
        va="center"
    )

    plt.axis("off")

    plt.savefig(
        "analytics/admin_chart.png",
        dpi=120,
        bbox_inches="tight"
    )

    exit()

# ==========================================
# SRMS THEME
# ==========================================

BACKGROUND = "#F1F5F9"
CARD = "#FFFFFF"
PRIMARY = "#2563EB"
TEXT = "#0F172A"
GRID = "#CBD5E1"

# ==========================================
# CHART
# ==========================================

fig = plt.figure(
    figsize=(12, 6),
    facecolor=BACKGROUND
)

ax = plt.gca()

ax.set_facecolor(CARD)

bars = plt.bar(
    df["name"],
    df["average_marks"],
    color=PRIMARY
)

# ==========================================
# VALUE LABELS
# ==========================================

for bar in bars:

    height = bar.get_height()

    plt.text(
        bar.get_x() + bar.get_width() / 2,
        height + 1,
        f"{height:.1f}",
        ha="center",
        fontsize=9,
        color=TEXT
    )

# ==========================================
# TITLES
# ==========================================

plt.title(
    "Top 10 Students Performance",
    fontsize=18,
    fontweight="bold",
    color=TEXT,
    pad=20
)

plt.xlabel(
    "Students",
    fontsize=12,
    color=TEXT
)

plt.ylabel(
    "Average Marks",
    fontsize=12,
    color=TEXT
)

# ==========================================
# AXIS STYLE
# ==========================================

plt.xticks(
    rotation=25,
    fontsize=10
)

plt.yticks(
    fontsize=10
)

ax.spines["top"].set_visible(False)
ax.spines["right"].set_visible(False)

ax.spines["left"].set_color(GRID)
ax.spines["bottom"].set_color(GRID)

plt.grid(
    axis="y",
    linestyle="--",
    alpha=0.4
)

plt.tight_layout()

# ==========================================
# SAVE
# ==========================================

plt.savefig(
    "analytics/admin_chart.png",
    dpi=120,
    bbox_inches="tight",
    facecolor=BACKGROUND
)

plt.close()