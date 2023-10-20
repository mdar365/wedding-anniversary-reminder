from datetime import datetime

import dateutil.parser as parser


class CoupleRecord:
    def __init__(self, id, wedding_date=None):
        self.id = id
        self.wedding_date = wedding_date


class AnniversaryReminderRecord:
    def __init__(self, id, next_wedding_anniversary):
        self.id = id
        self.next_wedding_anniversary = next_wedding_anniversary


anniversary_reminder_weeks = 2
special_case_anniversary_configs = dict({
    5: anniversary_reminder_weeks + 1,
    10: anniversary_reminder_weeks + 2
})


def generate_reminder_records(current_date, records):
    for record in records:
        print("ID: " + record.id)
        date = parse_wedding_date(record.wedding_date)
        if date is None: continue


def parse_wedding_date(date):
    try:
        return datetime.fromisoformat(date)
    except:
        return None
