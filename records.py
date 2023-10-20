from datetime import datetime

import dateutil.parser as parser
from dateutil import rrule


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
    reminder_records = []
    for record in records:
        print("ID: " + record.id)
        wedding_date = parse_wedding_date(record.wedding_date)
        if wedding_date is None: continue
        next_anniversary = wedding_date.copy(year=current_date.year)
        weeks_left = rrule.rrule(rrule.WEEKLY, dtstart=start_date, until=end_date)

        anniversary_number = current_date.year - wedding_date.year
        reminder_weeks = get_reminder_weeks(anniversary_number)


def parse_wedding_date(date):
    try:
        return datetime.fromisoformat(date)
    except:
        return None


def get_reminder_weeks(anniversary_number):
    list_of_keys = list(special_case_anniversary_configs.keys())
    list_of_keys.sort(reverse=True)
    for key in list_of_keys:
        if anniversary_number % key == 0: return special_case_anniversary_configs[key]
    return anniversary_reminder_weeks


def get_next_anniversary_date(current_date, wedding_date):
    potential_next_anniversary_date = wedding_date
    potential_next_anniversary_date.year = current_date.year
