from setuptools import setup, find_packages

setup(
    name = "elin_sync",
    version = "1.0",
    url = 'http://scv119.me',
    license = 'Private',
    description = "",
    author = '',
    packages = ["elin","config"],
    install_requires = ['setuptools',
                        'tornado',
                        'redis',
                        ],
    entry_points="""
    [console_scripts]
    sync-daemon=elin.app:main
    """,
)
