import unittest

from records import *


class RecordsTestCases(unittest.TestCase):
    def test_something(self):
        record = CoupleRecord("1234", None)
        generate_reminder_records(1, [record])  # add assertion here


if __name__ == '__main__':
    unittest.main()
