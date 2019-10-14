import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-list-config',
  templateUrl: './list-config.component.html',
  styleUrls: ['./list-config.component.scss']
})
export class ListConfigComponent implements OnInit {
  configFiles = {
    applications: [
      { key: 'key1', value: 'value 1' },
      { key: 'key2', value: 'value 2' },
      { key: 'key3', value: 'value 3' },
      { key: 'key4', value: 'value 4' },
      { key: 'key5', value: 'value 5' },
      { key: 'key6', value: 'value 6' },
      { key: 'key7', value: 'value 7' },
      { key: 'key8', value: 'value 8' },
      { key: 'key9', value: 'value 9' },
      { key: 'key10', value: 'value 10' },
      { key: 'key11', value: 'value 11' },
      { key: 'key12', value: 'value 12' },
      { key: 'key13', value: 'value 13' }
    ],

    DS: [
      { key: 'key1', value: 'value 1' },
      { key: 'key2', value: 'value 2' },
      { key: 'key3', value: 'value 3' },
      { key: 'key4', value: 'value 4' },
      { key: 'key5', value: 'value 5' },
      { key: 'key6', value: 'value 6' }
    ]
  };

  constructor() {}

  ngOnInit() {}
}
