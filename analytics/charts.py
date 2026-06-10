import sqlite3
import pandas as pd
import matplotlib.pyplot as plt
import sys

# ==========================================
# DEPARTMENT ARGUMENT
# ==========================================

department_name = sys.argv[1]

# ==========================================
# DATABASE CONNECTION
# ==========================================

conn = sqlite3.connect(
    "database/srms.db"
)

query = """
SELECT
    s.name,
    AVG(m.marks) AS average_marks
FROM students s
JOIN departments d
    ON s.department_id = d.id
JOIN marks m
    ON s.student_id = m.student_id
WHERE d.department_name = ?
GROUP BY s.student_id
ORDER BY average_marks DESC
"""

df = pd.read_sql_query(
    query,
    conn,
    params=(department_name,)
)

conn.close()

# ==========================================
# SRMS COLOR THEME
# ==========================================

BACKGROUND = "#F1F5F9"
CARD =       "#FFFFFF"
PRIMARY =    "#2563EB"
TEXT =       "#0F172A"
GRID =       "#CBD5E1"

# ==========================================
# NO DATA CASE
# ==========================================

if len(df) == 0:

    plt.figure(
        figsize=(10, 6),
        facecolor=BACKGROUND
    )

    plt.text(
        0.5,
        0.5,
        f"No Student Data Available\nfor {department_name}",
        fontsize=18,
        ha="center",
        va="center",
        color=TEXT
    )

    plt.axis("off")

    plt.savefig(
        "analytics/chart.png",
        dpi=100,
        facecolor=BACKGROUND
    )

    plt.close()

    exit()

# ==========================================
# CREATE FIGURE
# ==========================================

fig, ax = plt.subplots(
    figsize=(10, 6)
)

fig.patch.set_facecolor(BACKGROUND)

ax.set_facecolor(CARD)

# ==========================================
# BAR CHART
# ==========================================

bars = ax.bar(
    df["name"],
    df["average_marks"],
    color=PRIMARY
)

# ==========================================
# VALUE LABELS
# ==========================================

for bar in bars:

    height = bar.get_height()

    ax.text(
        bar.get_x() + bar.get_width() / 2,
        height + 1,
        f"{height:.1f}",
        ha="center",
        va="bottom",
        fontsize=9,
        color=TEXT
    )

# ==========================================
# TITLE
# ==========================================

ax.set_title(
    f"{department_name} Department Analytics",
    fontsize=18,
    fontweight="bold",
    color=TEXT,
    pad=20
)

# ==========================================
# AXIS LABELS
# ==========================================

ax.set_xlabel(
    "Students",
    fontsize=12,
    color=TEXT
)

ax.set_ylabel(
    "Average Marks",
    fontsize=12,
    color=TEXT
)

# ==========================================
# TICKS
# ==========================================

plt.xticks(
    rotation=25,
    fontsize=10
)

plt.yticks(
    fontsize=10
)

# ==========================================
# GRID
# ==========================================

ax.grid(
    axis="y",
    linestyle="--",
    alpha=0.4
)

# ==========================================
# BORDER STYLING
# ==========================================

ax.spines["top"].set_visible(False)
ax.spines["right"].set_visible(False)

ax.spines["left"].set_color(GRID)
ax.spines["bottom"].set_color(GRID)

# ==========================================
# LAYOUT
# ==========================================

plt.tight_layout()

# ==========================================
# SAVE IMAGE
# ==========================================

plt.savefig(
    "analytics/chart.png",
    dpi=100,
    facecolor=BACKGROUND
)

plt.close()