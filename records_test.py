import unittest

from records import *


class RecordsTestCases(unittest.TestCase):
    def test_reminder_weeks_if_anniversary_is_multiple_of_10(self):
        reminder_weeks = get_reminder_weeks(10)
        self.assertEqual(4, reminder_weeks)

    def test_reminder_weeks_if_anniversary_is_multiple_of_5(self):
        reminder_weeks = get_reminder_weeks(5)
        self.assertEqual(3, reminder_weeks)


if __name__ == '__main__':
    unittest.main()
